package com.edushare.file_sharing_app_backend.dto;

import com.edushare.file_sharing_app_backend.model.Comment;
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
