package com.absorbedose.filemgmt.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.absorbedose.filemgmt.reponse.FileUploadResponse;
import com.absorbedose.filemgmt.service.FileStorageService;

@RestController
public class FileController {

	private static final Logger logger = LoggerFactory.getLogger(FileController.class);

	@Autowired
	private FileStorageService fileStorageService;

	/**
	 * @author absorbedose
	 * @param fileName
	 * @param file
	 * @return
	 */
	@PostMapping("/uploadFile")
	public FileUploadResponse uploadFile(@RequestParam String fileName, @RequestParam("file") MultipartFile file) {
		fileName = fileStorageService.loadFile(fileName, file);

		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/downloadFile/")
				.path(fileName).toUriString();

		StringBuilder fileContent = new StringBuilder();

		try {
			URL filePath = new URL(fileDownloadUri);
			BufferedReader br = new BufferedReader(new InputStreamReader(filePath.openStream()));

			String inputLine;
			while ((inputLine = br.readLine()) != null) {
				fileContent.append(inputLine).append("\n");
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new FileUploadResponse(fileName, fileDownloadUri, file.getContentType(), file.getSize(), fileContent);
	}

	/**
	 * @author absorbedose
	 * @param fileName
	 * @param request
	 * @return
	 */
	@GetMapping("/downloadFile/{fileName:.+}")
	public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
		Resource resource = fileStorageService.loadFileAsResource(fileName);
		String contentType = null;
		try {
			contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
		} catch (IOException ex) {
			logger.info("Could not determine file type.");
		}
		if (contentType == null) {
			contentType = "application/octet-stream";
		}
		return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}
}