package com.developerscambodia.devkhmediaservice.file;

import com.developerscambodia.devkhmediaservice.file.web.MetaDataDto;
import io.minio.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class FileServiceImpl implements FileService {
    private final MinioClient minioClient;
    private final FileRepository fileRepository;

    private final MetaDataMapper metaDataMapper;


    @Override
    public Mono<MetaDataDto> uploadFile(MultipartFile file, String bucketName,MetaDataDto metaDataDto) throws IOException {
        return Mono.fromRunnable(()->{
            String uniqueIdentifier = UUID.randomUUID().toString();
            String objectName = uniqueIdentifier+"_"+ file.getOriginalFilename();

                try {
                    PutObjectArgs putObjectArgs= PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .stream(file.getInputStream(),file.getSize(),-1)
                            .build();


                    minioClient.putObject(putObjectArgs);
                    FileMetaData fileMetaData = metaDataMapper.toFileMetaData(metaDataDto);
                    fileMetaData.setUuid(UUID.randomUUID().toString());
                    fileMetaData.setFileName(objectName);
                    fileMetaData.setFileSize(file.getSize());
                    fileMetaData.setContentType(file.getContentType());
                    fileRepository.save(fileMetaData);


                }catch (Exception e){
                    throw new RuntimeException("Failed to upload file!!!", e);
                }


        });


    }

    @Override
    public Flux<List<MetaDataDto>> uploadFiles(Flux<MultipartFile> files, String bucketName,MetaDataDto metaDataDto){
        return files.flatMap(file -> Mono.fromCallable(() ->{
            String objectName = file.getOriginalFilename();
            try {
                PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                        .bucket(bucketName)
                        .object(objectName)
                        .stream(file.getInputStream(), file.getSize(), -1)
                        .build();
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }));


    }

    @Override
    public Flux<ObjectWriteResponse> uploadMultipleFiles(Flux<MultipartFile> files, String bucketName) {
        return files.publishOn(Schedulers.boundedElastic()).publishOn(Schedulers.boundedElastic()).flatMap(file -> {
            String objectName = file.getOriginalFilename();
            List<ObjectWriteResponse> writeResponses = new ArrayList<>();

            try {


                // Upload the file to Minio
                PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                        .bucket(bucketName)
                        .object(objectName)
                        .stream(file.getInputStream(), file.getSize(), -1)
                        .build();
                minioClient.putObject(putObjectArgs);

                FileMetaData fileMetaData = new FileMetaData();
                fileMetaData.setUuid(UUID.randomUUID().toString());
                fileMetaData.setFileSize(file.getSize());
                fileMetaData.setFileName(objectName);
                fileMetaData.setContentType(file.getContentType());
                fileMetaData.setCreatedAt(LocalDateTime.now());
                fileMetaData.setIsDeleted(false);
                fileMetaData.setLastModifiedAt(LocalDateTime.now());

                // Save the file metadata to a list
                fileRepository.save(fileMetaData);
            } catch (Exception e) {
                return Mono.error(e);
            }

            // Convert the list of write responses to a Flux
            return Flux.fromIterable(writeResponses);
        });
    }

    @Override
    public void disableFile(String uuid) {
        FileMetaData fileMetaData = fileRepository.findByUuid(uuid);
        fileMetaData.setIsDeleted(true);
        fileRepository.save(fileMetaData);
    }

    @Override
    public void deleteFile(String uuid,Boolean status, String bucketName) {
        String objectName = fileRepository.findByUuid(uuid).getFileName();

        try {
            minioClient.removeObject(RemoveObjectArgs.builder()
                    .bucket(bucketName)
                    .object(objectName)
                    .build());
//            fileRepository.findByUuidAndIsDeleted(uuid,isDeleted);
            fileRepository.delete(fileRepository.findByUuid(uuid));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
