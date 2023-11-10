package com.developerscambodia.devkhmediaservice.file;



import com.developerscambodia.devkhmediaservice.file.web.MetaDataDto;
import io.minio.ObjectWriteResponse;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.File;
import java.io.IOException;


public interface FileService {

    Mono<MetaDataDto> uploadFile(MultipartFile file, String bucketName ,MetaDataDto metaDataDto) throws IOException;
    Flux<?> uploadFiles(Flux<MultipartFile> files, String bucketName,MetaDataDto metaDataDto);

    Flux<ObjectWriteResponse> uploadMultipleFiles(Flux<MultipartFile> files, String bucketName) throws IOException;



    void disableFile(String uuid);
    @Transactional
    void deleteFile(String uuid,Boolean status, String bucketName);

}
