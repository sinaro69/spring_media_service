package com.developerscambodia.devkhmediaservice.file.web;

import lombok.Value;

import java.time.LocalDateTime;


public record MetaDataDto(
        String uuid,
        String fileName,
        String contentType,
        Long fileSize,
        Boolean isDeleted,
        LocalDateTime createdAt,
        LocalDateTime lastModifiedAt



) {

}
