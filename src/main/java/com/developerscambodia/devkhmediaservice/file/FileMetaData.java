package com.developerscambodia.devkhmediaservice.file;

import com.developerscambodia.devkhmediaservice.base.BasedEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class FileMetaData extends BasedEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String uuid;
    private String fileName;
    private Long fileSize;
    private String contentType;



}
