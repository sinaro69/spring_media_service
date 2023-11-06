package com.developerscambodia.devkhmediaservice.file.web;


import com.developerscambodia.devkhmediaservice.file.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RestController
@RequestMapping(value = "api/v1/video",produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class VideoUploadController {

    private final FileService fileService;

    @PostMapping
    public ResponseEntity<Object> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {

        if (!file.isEmpty()) {
            try {
                fileService.uploadFile(file, "video-upload");
                return ResponseEntity.ok()
                        .build();

            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.badRequest().build();
            }
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("disable/{uuid}")
    public ResponseEntity<Object> disableFile(@PathVariable String uuid) {
        fileService.disableFile(uuid);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("delete/{uuid}")
    public ResponseEntity<Object> deleteFile(@PathVariable String uuid) {
        fileService.deleteFile(uuid, "video-upload");
        return ResponseEntity.ok().build();
    }
}
