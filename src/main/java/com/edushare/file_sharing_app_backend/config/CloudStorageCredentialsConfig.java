package com.edushare.file_sharing_app_backend.config;


import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;

@Configuration
@Data
@Slf4j
public class CloudStorageCredentialsConfig {
    @Value("${spring.gcp.storage.credentials.credentialsPath}")
    private String credentialsPath;

    @Bean
    public Storage storage() throws IOException {
        try{
            InputStream serviceAccountStream = new ClassPathResource(credentialsPath).getInputStream();
            GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccountStream);
            return StorageOptions.newBuilder().setCredentials(credentials).build().getService();
        } catch (IOException e){
            log.error("Error loading Google Cloud Storage credentials", e);
            throw e;
        }

    }
}
