package com.example.cs319project.file;

import com.example.cs319project.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileDBRepository extends JpaRepository<FileDB, String> {
    FileDB findAllByPhotoOwner(Student student);
}
