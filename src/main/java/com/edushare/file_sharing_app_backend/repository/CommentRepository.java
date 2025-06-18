package com.edushare.file_sharing_app_backend.repository;


import com.edushare.file_sharing_app_backend.model.Comment;
import com.edushare.file_sharing_app_backend.model.FileMetadata;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByFile(FileMetadata file);
}
