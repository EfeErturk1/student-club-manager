package com.example.cs319project.service.impl;

import com.example.cs319project.model.Document;
import com.example.cs319project.repository.DocumentRepository;
import com.example.cs319project.service.DocumentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Service
public class DocumentServiceImpl implements DocumentService{

    private final DocumentRepository repository;

    @Override
    public Document findByDocumentId(Integer id){
        return repository.findByDocumentId(id);
    }

    @Override
    public Document addDocument(Document document) {
        Objects.requireNonNull(document, "club cannot be null");
        return repository.save(document);
    }

    @Override
    public void removeDocument(Document document) {
        repository.delete(document);
    }

    @Override
    public List<Document> findAll() {
        return repository.findAll();
    }
}
