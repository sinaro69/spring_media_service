package com.developerscambodia.devkhmediaservice.file;

import com.developerscambodia.devkhmediaservice.file.web.MetaDataDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MetaDataMapper {


    MetaDataDto toMetaData(FileMetaData fileMetaData);
    FileMetaData toFileMetaData(MetaDataDto metaDataDto);
}
