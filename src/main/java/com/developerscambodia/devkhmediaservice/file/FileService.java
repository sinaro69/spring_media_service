package com.developerscambodia.devkhmediaservice.file;



import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;


public interface FileService {

    String uploadFile(MultipartFile file, String bucketName) throws IOException;
//    String uploadImage(MultipartFile file) throws IOException;

//    MetaDataDto findByUuid(String uuid);


    void disableFile(String uuid);
    @Transactional
    void deleteFile(String uuid, String bucketName);
    void saveFileMataData(String fileName);
}
