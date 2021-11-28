package com.example.demo.FileLogic;

import com.example.demo.Entity.FileDB;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileDBRepository extends JpaRepository<FileDB, String> {

}
