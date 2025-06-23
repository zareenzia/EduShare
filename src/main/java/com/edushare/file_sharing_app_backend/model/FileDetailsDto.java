package com.edushare.file_sharing_app_backend.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FileDetailsDto {
    private FileMetadataDto fileMetadata;
    private List<Comment> comments;
}
