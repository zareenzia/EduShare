package com.edushare.file_sharing_app_backend.repository;

import com.edushare.file_sharing_app_backend.model.FileMetadata;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileMetadataRepository extends JpaRepository<FileMetadata, Long> {
    List<FileMetadata> findByFileNameIn(List<String> filenames);
}
