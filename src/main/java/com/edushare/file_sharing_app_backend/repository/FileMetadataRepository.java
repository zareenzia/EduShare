package com.edushare.file_sharing_app_backend.repository;

import com.edushare.file_sharing_app_backend.model.FileMetadata;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileMetadataRepository extends JpaRepository<FileMetadata, Long> {
}
