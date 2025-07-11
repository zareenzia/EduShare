package com.edushare.file_sharing_app_backend.controller;

import com.edushare.file_sharing_app_backend.model.FileDetailsDto;
import com.edushare.file_sharing_app_backend.model.FileMetadata;
import com.edushare.file_sharing_app_backend.model.PaginatedResponse;
import com.edushare.file_sharing_app_backend.repository.FileMetadataRepository;
import com.edushare.file_sharing_app_backend.service.GCSFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
public class FileUploadController {

    private final GCSFileService fileService;
    private final FileMetadataRepository repository;

    public static final String API_PATH_FILE_LIST = "/list";
    public static final String API_PATH_FILE_UPLOAD = "/upload";
    public static final String API_PATH_FILE_DOWNLOAD = "/download";
    public static final String API_PATH_FILE_COUNT = "/fileCount";
    public static final String API_PATH_FILE_DETAILS = "/fileDetails/{fileId}";

    @PostMapping(path = API_PATH_FILE_UPLOAD)
    public ResponseEntity<String> uploadFileWithMetadata(
            @RequestParam("file") MultipartFile file,
            @RequestParam("title") String title,
            @RequestParam("courseName") String courseName,
            @RequestParam("instructor") String instructor,
            @RequestParam("semester") String semester,
            @RequestParam("courseCode") String courseCode,
            @RequestParam("department") String department,
            @RequestParam(value = "tags", required = false) String tags
    ) {
        try {
            String gcsUrl = fileService.uploadFile(file, file.getOriginalFilename());

            FileMetadata metadata = FileMetadata.builder()
                    .title(title)
                    .courseName(courseName)
                    .instructor(instructor)
                    .courseCode(courseCode)
                    .department(department)
                    .semester(semester)
                    .tags(tags)
                    .fileName(file.getOriginalFilename())
                    .fileType(file.getContentType())
                    .fileSize(file.getSize())
                    .uploadedAt(LocalDateTime.now())
                    .gcsUrl(gcsUrl)
                    .build();

            fileService.saveMetadata(metadata);
            return ResponseEntity.ok("File uploaded and metadata saved.");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Upload failed");
        }
    }

    @GetMapping(path = API_PATH_FILE_DOWNLOAD)
    public ResponseEntity<byte[]> download(@RequestParam("fileName") String fileName) {
        try {
            byte[] fileData = fileService.downloadFile(fileName);

            MediaType mediaType = MediaTypeFactory.getMediaType(fileName)
                    .orElse(MediaType.APPLICATION_OCTET_STREAM);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(fileData);

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping(path = API_PATH_FILE_LIST)
    public ResponseEntity<PaginatedResponse<FileMetadata>> listFiles(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "100") int size,
            @RequestParam(required = false) String searchTerm
    ) {
        try {
            PaginatedResponse<FileMetadata> response = fileService.listAllFilesWithMetadata(page, size, searchTerm);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping(path = API_PATH_FILE_COUNT)
    public ResponseEntity<Map<String, Long>> getFileCount() {
        long count = repository.count();
        return ResponseEntity.ok(Collections.singletonMap("count", count));
    }

    @GetMapping(path = API_PATH_FILE_DETAILS)
    public FileDetailsDto getFileDetails(@PathVariable Long fileId) {
        return fileService.getFileDetails(fileId);
    }
}
