package com.edushare.file_sharing_app_backend.service;

import com.edushare.file_sharing_app_backend.model.FileMetadata;
import com.edushare.file_sharing_app_backend.model.PaginatedResponse;
import com.edushare.file_sharing_app_backend.repository.FileMetadataRepository;
import com.google.cloud.storage.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class GCSFileService {

    private final Storage storage;
    private final FileMetadataRepository metadataRepository;
    @Value("${spring.gcp.bucket.name}")
    private String bucketName;

    public String uploadFile(MultipartFile file, String fileName) throws IOException {
        Blob blob = storage.create(BlobInfo.newBuilder(bucketName, fileName).build(), file.getBytes());
        return blob.getMediaLink();
    }

    public void saveMetadata(FileMetadata metadata) {
        metadataRepository.save(metadata);
    }

    public byte[] downloadFile(String fileName) throws IOException {
        Blob blob = storage.get(BlobId.of(bucketName, fileName));
        if (blob == null) {
            throw new IOException("File not found in GCP bucket");
        }
        return blob.getContent();
    }

    public PaginatedResponse<FileMetadata> listAllFilesWithMetadata(int page, int size, String searchTerm) {

        List<FileMetadata> allMetadata;
        if (searchTerm != null && !searchTerm.trim().isEmpty()) {
            allMetadata = metadataRepository.searchByKeyword(searchTerm);
        } else {
            Bucket bucket = storage.get(bucketName);
            Iterable<Blob> blobs = bucket.list().iterateAll();
            List<String> allFilenames = StreamSupport.stream(blobs.spliterator(), false)
                    .map(Blob::getName).collect(Collectors.toList());

            if (allFilenames.isEmpty()) {
                return new PaginatedResponse<>(Collections.emptyList(), page, size, 0);
            }

            allMetadata = metadataRepository.findByFileNameIn(allFilenames);
        }
        allMetadata.sort(Comparator.comparing(FileMetadata::getUploadedAt).reversed());

        int totalItems = allMetadata.size();
        int totalPages = (int) Math.ceil((double) totalItems / size);
        int fromIndex = Math.min(page * size, totalItems);
        int toIndex = Math.min(fromIndex + size, totalItems);

        List<FileMetadata> paginatedList = allMetadata.subList(fromIndex, toIndex);

        return new PaginatedResponse<>(paginatedList, page, size, totalPages);
    }

}
