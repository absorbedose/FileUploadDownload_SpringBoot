package com.absorbedose.filemgmt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.absorbedose.filemgmt.property.FileStorageProperties;

@SpringBootApplication
@EnableConfigurationProperties({
		FileStorageProperties.class
})
public class FileMgmtApplication {

	public static void main(String[] args) {
		SpringApplication.run(FileMgmtApplication.class, args);
	}
}
