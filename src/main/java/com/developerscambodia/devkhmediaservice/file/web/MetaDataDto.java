package com.developerscambodia.devkhmediaservice.file.web;

import lombok.Value;


public record MetaDataDto(
        String uuid,
        String fileName,
        String contentType,
        Long fileSize



) {

}
