package com.edushare.file_sharing_app_backend.config;


import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Component
@EnableConfigurationProperties
@Data
@Slf4j
@ConfigurationProperties(prefix = "gcp.storage.credentials")
public class CloudStorageCredentialsConfig {
    @Value("${gcp.credentials.path}")
    private String credentialsPath;

    @Bean
    public Storage storage() throws IOException {
        try{
            InputStream serviceAccountStream = new ClassPathResource(credentialsPath).getInputStream();
            GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccountStream);
            return StorageOptions.newBuilder().setCredentials(credentials).build().getService();
        } catch (IOException e){
            log.warn("Error !!!!!!!!");
            throw e;
        }

    }
}
