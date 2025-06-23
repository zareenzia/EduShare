package com.edushare.file_sharing_app_backend.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FileMetadataDto {
    private Long id;
    private String title;
    private String courseName;
    private String courseCode;
    private String department;
    private String instructor;
    private String semester;
    private String tags;
    private String fileName;
    private String fileType;
    private Long fileSize;
    private LocalDateTime uploadedAt;
    private String gcsUrl;
}
