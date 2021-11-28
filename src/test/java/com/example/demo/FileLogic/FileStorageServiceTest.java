package com.example.demo.FileLogic;

import org.apache.commons.io.IOUtils;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mock.web.MockMultipartFile;

import static org.mockito.ArgumentMatchers.any;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class FileStorageServiceTest {

    @InjectMocks
    private FileStorageService service;

    @Mock
    private FileDBRepository repository;


    @Test
    public void souldStoredFileToDb() throws IOException {
        File uploadFile = new File("src/main/resources/static/Cat03.jpg");
        FileInputStream is = new FileInputStream(uploadFile);
        MockMultipartFile mockMultipartFile = new MockMultipartFile("Cat03.jpg",
                "Cat03.jpg", "image/jpg", IOUtils.toByteArray(is));

        service.store(mockMultipartFile);

        verify(repository, times(1)).save(any());
    }

    @Test
    public void shouldNotStoredFileToDbWhenFileTypeNotContains() throws IOException {
        File uploadFile = new File("src/main/resources/static/test.html");
        FileInputStream is = new FileInputStream(uploadFile);
        MockMultipartFile mockMultipartFile = new MockMultipartFile("test.html", "test.html",
                "text/html", IOUtils.toByteArray(is));

        try {
            service.store(mockMultipartFile);
            fail();
        } catch (FileUploadException fue) {
            verify(repository, times(0)).save(any());
        }
    }
}