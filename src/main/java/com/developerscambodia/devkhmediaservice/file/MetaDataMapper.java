package com.developerscambodia.devkhmediaservice.file;

import com.developerscambodia.devkhmediaservice.file.web.MetaDataDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MetaDataMapper {


    FileMetaData toFileMetaData(MetaDataDto metaDataDto);
}
