package com.example.cs319project.service.impl;

import com.example.cs319project.model.Assignment;
import com.example.cs319project.model.Document;
import com.example.cs319project.repository.AssignmentRepository;
import com.example.cs319project.service.AssignmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class AssignmentServiceImpl implements AssignmentService {

    private final AssignmentRepository repository;

    @Override
    public Assignment findByAssignmentId(Integer id) {
        return repository.findByAssignmentId(id);
    }

    @Override
    public Assignment createNewAssignment(Assignment assignment) {
        Objects.requireNonNull(assignment, "Assignment cannot be null");
        return repository.save(assignment);
    }

    public void deleteAssignment(Assignment assignment) {
        repository.delete(assignment);
    }

    @Override
    public List<Assignment> findAll() {
        return repository.findAll();
    }

    @Override
    public Assignment findAssignmentHasDocument(Document document) {
        List<Document> documents = new ArrayList<>();
        documents.add(document);
        return repository.findAllByDocumentsIn(documents);
    }
}
