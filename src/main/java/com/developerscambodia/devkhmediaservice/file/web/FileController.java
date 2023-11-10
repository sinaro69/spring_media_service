package com.developerscambodia.devkhmediaservice.file.web;

import com.developerscambodia.devkhmediaservice.file.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "api/v1/file",produces = "application/json")
public class FileController {
    private final FileService fileService;
    @PostMapping
    public Mono<ResponseEntity<String>> uploadFile(@RequestParam("file") MultipartFile file, MetaDataDto metaDataDto) throws IOException {

        if (!file.isEmpty()) {
            try {
                return fileService.uploadFile(file, "file-upload", metaDataDto).then(Mono.just(ResponseEntity.ok("File uploaded successfully")));
            } catch (Exception e) {
                e.printStackTrace();
                return Mono.just(ResponseEntity.status(500).body("Failed ot upload file!!!"));
            }

        } else {
            return Mono.just(ResponseEntity.status(400).body("Invalid file"));
        }

    }
    @PostMapping("/upload")
    public Flux<ResponseEntity<String>> uploadFiles(@RequestPart("files") Flux<MultipartFile> files) {
        return files
                .flatMap(file -> {
                            try {
                                return fileService.uploadMultipleFiles(files, "file-upload")
                                        .map(response -> ResponseEntity.ok("File uploaded successfully"))
                                        .onErrorReturn(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload files"));
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                );
    }


    @DeleteMapping("disable/{uuid}")
    public ResponseEntity<Object> disableFile(@PathVariable String uuid) {
        fileService.disableFile(uuid);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("delete/{uuid}")
    public ResponseEntity<Object> deleteFile(@PathVariable String uuid,Boolean status) {
        fileService.deleteFile(uuid,status, "file-upload");
        return ResponseEntity.ok().build();
    }
}
