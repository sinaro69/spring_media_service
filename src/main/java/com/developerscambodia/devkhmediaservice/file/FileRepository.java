package com.developerscambodia.devkhmediaservice.file;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface FileRepository extends JpaRepository<FileMetaData, Long> {

     FileMetaData findByUuid(String uuid);



}
