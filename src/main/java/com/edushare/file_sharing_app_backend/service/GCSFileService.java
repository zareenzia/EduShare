package com.edushare.file_sharing_app_backend.service;

import com.edushare.file_sharing_app_backend.model.FileMetadata;
import com.edushare.file_sharing_app_backend.repository.FileMetadataRepository;
import com.google.cloud.storage.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class GCSFileService {

    @Value("${spring.gcp.bucket.name}")
    private String bucketName;

    private final Storage storage;
    private final FileMetadataRepository metadataRepository;

    public String uploadFile(MultipartFile file, String fileName) throws IOException {
        Blob blob = storage.create(
                BlobInfo.newBuilder(bucketName, fileName).build(),
                file.getBytes()
        );
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

    public List<FileMetadata> listAllFilesWithMetadata(int page, int size) {
        Bucket bucket = storage.get(bucketName);
        Iterable<Blob> blobs = bucket.list().iterateAll();

        List<String> allFilenames = StreamSupport.stream(blobs.spliterator(), false)
                .map(Blob::getName)
                .collect(Collectors.toList());

        Collections.sort(allFilenames);
        int fromIndex = Math.min(page * size, allFilenames.size());
        int toIndex = Math.min(fromIndex + size, allFilenames.size());

        //TODO: Pagination is not working properly
        List<String> paginatedFilenames = allFilenames.subList(fromIndex, toIndex);
        Sort sort = Sort.by(Sort.Direction.DESC, "uploadedAt");
        return metadataRepository.findByFileNameIn(paginatedFilenames, sort);
    }

}
