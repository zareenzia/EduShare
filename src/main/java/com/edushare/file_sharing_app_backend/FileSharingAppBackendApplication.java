package com.edushare.file_sharing_app_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication
public class FileSharingAppBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(FileSharingAppBackendApplication.class, args);
	}

}
