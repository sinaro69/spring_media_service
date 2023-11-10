package com.developerscambodia.devkhmediaservice.file.web;

import com.developerscambodia.devkhmediaservice.file.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "api/v1/image",produces = "application/json")
public class ImageController {

    private final FileService fileService;
    @PostMapping
    public Mono<ResponseEntity<String>> uploadFile(@RequestParam("file") MultipartFile file, MetaDataDto metaDataDto) throws IOException {

        if (!file.isEmpty()) {
            try {
                return fileService.uploadFile(file, "image-upload", metaDataDto).then(Mono.just(ResponseEntity.ok("File uploaded successfully")));
            } catch (Exception e) {
                e.printStackTrace();
                return Mono.just(ResponseEntity.status(500).body("Failed ot upload file!!!"));
            }

        } else {
            return Mono.just(ResponseEntity.status(400).body("Invalid file"));
        }

    }
}

