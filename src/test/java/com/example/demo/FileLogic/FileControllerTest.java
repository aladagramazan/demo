package com.example.demo.FileLogic;

import com.example.demo.Entity.FileDB;
import com.example.demo.Response.ResponseMessage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class FileControllerTest {

    @InjectMocks
    private FileController controller;

    @Mock
    private FileStorageService service;


    @Test
    public void shouldStorageFileToDb() throws IOException {
        MockMultipartFile file = new MockMultipartFile("file.xlsx", "file.xlsx", "data", new byte[]{-128, 2, 10, 122, 127});
        ResponseEntity<ResponseMessage> entity = controller.uploadFile(file);
        assertEquals("Uploaded the file successfully: file.xlsx", entity.getBody().getMessage());
        assertEquals(200, entity.getStatusCode().value());
        verify(service).store(file);
    }

    @Test
    public void shouldGetFilesFromDb() {
        FileDB fileDB1 = FileDB.builder()
                .type("jpg")
                .id("ekwmer")
                .name("file1")
                .build();

        when(service.getFile(any())).thenReturn(fileDB1);

        ResponseEntity<byte[]> entity = controller.getFile("ekwmer");

        assertEquals(1, entity.getHeaders().values().size());
        assertEquals(200, entity.getStatusCodeValue());
    }

    @Test
    public void shouldDeleteFilesFromDb() {
        FileDB fileDB1 = FileDB.builder()
                .type("jpg")
                .id("ekwmer")
                .name("file1")
                .build();

        when(service.getFile(any())).thenReturn(fileDB1);

        ResponseEntity<?> entity = controller.deleteFile("ekwmer");

        assertEquals(200, entity.getStatusCodeValue());
    }

}