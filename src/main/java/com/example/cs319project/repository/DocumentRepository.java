package com.example.cs319project.repository;

import com.example.cs319project.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DocumentRepository extends JpaRepository<Document, Integer> {
    List<Document> findAll();
    Document findByDocumentId(Integer documentId);
}
