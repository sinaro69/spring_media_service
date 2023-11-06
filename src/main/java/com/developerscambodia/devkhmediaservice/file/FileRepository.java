package com.developerscambodia.devkhmediaservice.file;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<FileMetaData, Long> {

    FileMetaData findByUuid(String uuid);

    FileMetaData deleteAllByUuid(String uuid);
}
