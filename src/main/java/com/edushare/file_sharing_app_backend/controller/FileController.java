package com.edushare.file_sharing_app_backend.controller;

import com.edushare.file_sharing_app_backend.model.FileMetadata;
import com.edushare.file_sharing_app_backend.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/files")
public class FileController {

    @Autowired
    private FileService fileService;

    @PostMapping("/upload")
    public ResponseEntity<FileMetadata> uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("title") String title,
            @RequestParam("courseName") String courseName,
            @RequestParam("instructor") String instructor,
            @RequestParam("semester") String semester,
            @RequestParam("tags") String tags) {
        try {
            FileMetadata savedFile = fileService.saveFile(file, title, courseName, instructor, semester, tags);
            return ResponseEntity.ok(savedFile);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
