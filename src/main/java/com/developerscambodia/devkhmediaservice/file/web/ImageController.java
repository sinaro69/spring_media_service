package com.developerscambodia.devkhmediaservice.file.web;

import com.developerscambodia.devkhmediaservice.file.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "api/v1/image",produces = "application/json")
public class ImageController {

    private final FileService fileService;
    @PostMapping
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {

        if (!file.isEmpty()) {
            try {
                fileService.uploadFile(file, "image-upload");
                return ResponseEntity.ok().build();
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.badRequest().build();
            }
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}

