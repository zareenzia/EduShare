package com.edushare.file_sharing_app_backend.repository;

import com.edushare.file_sharing_app_backend.model.FileMetadata;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FileMetadataRepository extends JpaRepository<FileMetadata, Long> {
    List<FileMetadata> findByFileNameIn(List<String> filenames);

    @Query("SELECT f FROM FileMetadata f WHERE " +
            "LOWER(f.title) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(f.courseName) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(f.courseCode) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(f.department) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(f.instructor) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(f.semester) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(f.tags) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(f.fileName) LIKE LOWER(CONCAT('%', :search, '%'))")
    List<FileMetadata> searchByKeyword(@Param("search") String search);

}
