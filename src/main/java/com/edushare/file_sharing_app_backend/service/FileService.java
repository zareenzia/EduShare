package com.edushare.file_sharing_app_backend.service;

import com.edushare.file_sharing_app_backend.model.FileMetadata;
import com.edushare.file_sharing_app_backend.repository.FileMetadataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class FileService {

    private final FileMetadataRepository repository;

    public FileMetadata saveFile(MultipartFile file, String title, String courseName, String instructor,
                                 String semester, String tags) throws Exception {
        FileMetadata metadata = FileMetadata.builder()
                .title(title)
                .courseName(courseName)
                .instructor(instructor)
                .semester(semester)
                .tags(tags)
                .fileName(file.getOriginalFilename())
                .fileType(file.getContentType())
                .fileSize(file.getSize())
                .uploadedAt(LocalDateTime.now())
                .data(file.getBytes())
                .build();

        return repository.save(metadata);
    }
}
