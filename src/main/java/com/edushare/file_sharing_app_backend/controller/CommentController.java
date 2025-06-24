package com.edushare.file_sharing_app_backend.controller;

import com.edushare.file_sharing_app_backend.model.Comment;
import com.edushare.file_sharing_app_backend.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    public static final String API_PATH_COMMENT_ADD = "/add";
    public static final String API_PATH_COMMENT_GET = "/file/{fileId}";
    private static final String API_PATH_COMMENT_DELETE = "/delete/{id}";


    @PostMapping(path = API_PATH_COMMENT_ADD, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Comment> addComment(@RequestParam Long fileId,
                                              @RequestParam String username,
                                              @RequestParam String text) {
        try {
            Comment saved = commentService.addComment(fileId, username, text);
            return ResponseEntity.ok(saved);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping(path = API_PATH_COMMENT_GET)
    public ResponseEntity<List<Comment>> getComments(@PathVariable Long fileId) {
        try {
            List<Comment> comments = commentService.getCommentsForFile(fileId);
            return ResponseEntity.ok(comments);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping(path = API_PATH_COMMENT_DELETE)
    public ResponseEntity<?> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return ResponseEntity.ok().build();
    }
}
