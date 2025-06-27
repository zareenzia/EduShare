package com.edushare.file_sharing_app_backend.service;

import com.edushare.file_sharing_app_backend.model.Comment;
import com.edushare.file_sharing_app_backend.model.FileMetadata;
import com.edushare.file_sharing_app_backend.repository.CommentRepository;
import com.edushare.file_sharing_app_backend.repository.FileMetadataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final FileMetadataRepository fileMetadataRepository;


    public Comment addComment(Long fileId, String username, String text) {
        FileMetadata file = fileMetadataRepository.findById(fileId)
                .orElseThrow(() -> new IllegalArgumentException("File not found"));

        Comment comment = Comment.builder()
                .file(file)
                .username(username)
                .text(text)
                .postedAt(LocalDateTime.now())
                .build();

        return commentRepository.save(comment);
    }

    public List<Comment> getCommentsForFile(Long fileId) {
        FileMetadata file = fileMetadataRepository.findById(fileId)
                .orElseThrow(() -> new IllegalArgumentException("File not found"));

        return commentRepository.findByFile(file);
    }

    public void deleteCommentById(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("Comment not found with ID: " + commentId));
        commentRepository.delete(comment);
    }
}
