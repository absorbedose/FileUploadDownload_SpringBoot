package com.absorbedose.filemgmt;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import com.absorbedose.filemgmt.controller.FileController;
import com.absorbedose.filemgmt.reponse.FileUploadResponse;

import static org.mockito.Mockito.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class FileDemoApplicationTests {
	@Test
	public void fileUploadTest1() {
		
		FileController test1 = mock(FileController.class);
		MultipartFile file = null;
		test1.uploadFile("absorbedose..12", file);
        when(test1.uploadFile("absorbedose..12", file)).thenReturn(new FileUploadResponse("absorbedose..12", "", "",10,new StringBuilder()));
	}

	@Test
	public void fileDownloadTest2() {
		
		FileController test2 = mock(FileController.class);
        test2.downloadFile("absorbedose.txt", null);
	}

}
