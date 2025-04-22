package com.edushare.file_sharing_app_backend.service;

import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class GCSFileService {

    @Value("${spring.gcp.bucket.name}")
    private String bucketName;

    private final Storage storage;

    public GCSFileService(Storage storage) {
        this.storage = storage;
    }

    public String uploadFile(MultipartFile file, String objectName) throws IOException {
        BlobId blobId = BlobId.of(bucketName, objectName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
                .setContentType(file.getContentType())
                .build();

        storage.create(blobInfo, file.getBytes());

        // Return public URL (if object is public)
        return String.format("https://storage.googleapis.com/%s/%s", bucketName, objectName);
    }
}
