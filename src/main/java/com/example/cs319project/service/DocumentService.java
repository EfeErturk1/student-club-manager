package com.example.cs319project.service;

import com.example.cs319project.model.Document;

import java.util.List;

public interface DocumentService {
    Document findByDocumentId(Integer id);
    Boolean addDocument(Document document);
    Boolean removeDocument(Integer documentId);
    List<Document> findAll();
    Boolean editDocument(Document document);
    void viewDocument(Integer id);
}
