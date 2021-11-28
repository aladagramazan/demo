package com.example.demo.FileLogic;


import com.example.demo.Entity.FileDB;
import com.example.demo.Entity.FileType;
import org.apache.commons.io.FilenameUtils;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class FileStorageService {

    @Autowired
    private FileDBRepository fileDBRepository;

    @Transactional
    public void store(MultipartFile file) throws IOException {
        String type = FilenameUtils.getExtension(file.getOriginalFilename());
        List<String> fileTypes = getFileTypes();
        if (!fileTypes.contains(type)) {
            throw new FileUploadException();
        }
        FileDB fileDB = FileDB.builder()
                .name(StringUtils.cleanPath(file.getOriginalFilename()))
                .type(FilenameUtils.getExtension(file.getOriginalFilename()))
                .data(file.getBytes())
                .build();

         fileDBRepository.save(fileDB);
    }

    private List<String> getFileTypes() {
        return Stream.of(FileType.values())
                .map(FileType::getName)
                .collect(Collectors.toList());
    }

    public FileDB getFile(String id) {
        Optional<FileDB>  file = fileDBRepository.findById(id);
        if (file.isPresent()) {
            return file.get();
        }

        return file.orElseThrow(() -> new IllegalArgumentException("Invalid File Id:" + id));

    }

    public void deleteFile(FileDB fileDB) {
        fileDBRepository.delete(fileDB);
    }

    public Stream<FileDB> getAllFiles() {
        return fileDBRepository.findAll().stream();
    }

}
