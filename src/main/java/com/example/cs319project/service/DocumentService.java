package com.example.cs319project.service;

import com.example.cs319project.model.Document;

import java.util.List;

public interface DocumentService {
    Document findByDocumentId(Integer id);
    Document addDocument(Document document);
    void removeDocument(Document document);
    List<Document> findAll();
}
