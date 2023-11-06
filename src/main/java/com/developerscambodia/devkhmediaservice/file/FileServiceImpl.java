package com.developerscambodia.devkhmediaservice.file;

import io.minio.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class FileServiceImpl implements FileService {
    private final MinioClient minioClient;
    private final FileRepository fileRepository;


    @Override
    public String uploadFile(MultipartFile file, String bucketName) throws IOException {
        String objectName = file.getOriginalFilename();

        try {

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
            fileRepository.save(fileMetaData);

            return objectName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public void disableFile(String uuid) {
        FileMetaData fileMetaData = fileRepository.findByUuid(uuid);
        fileMetaData.setIsDeleted(true);
        fileRepository.save(fileMetaData);
    }

    @Override
    public void deleteFile(String uuid, String bucketName) {
        String objectName = fileRepository.findByUuid(uuid).getFileName();
        try {
            minioClient.removeObject(RemoveObjectArgs.builder()
                    .bucket(bucketName)
                    .object(objectName)
                    .build());
            fileRepository.deleteAllByUuid(uuid);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveFileMataData(String fileName) {
//        FileMetaData fileMetaData = new FileMetaData();
//        fileMetaData.setUuid(UUID.randomUUID().toString());
//        fileMetaData.setFileSize(file.getSize());
//        fileMetaData.setFileName(objectName);
//        fileMetaData.setFileName(fileName);
//        fileMetaData.setCreatedAt(LocalDateTime.now());
//        fileMetaData.setIsDeleted(false);
//        fileRepository.save(fileMetaData);
    }


}
