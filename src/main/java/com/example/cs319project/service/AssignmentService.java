package com.example.cs319project.service;

import com.example.cs319project.model.Assignment;
import com.example.cs319project.model.Document;

import java.util.List;
import java.util.Optional;

public interface AssignmentService {
    Assignment findByAssignmentId(Integer id);
    Assignment createNewAssignment(Assignment assignment);
    void deleteAssignment(Assignment assignment);
    List<Assignment> findAll();
    Assignment findAssignmentHasDocument(Document document);
    Assignment saveAssignment(Assignment assignment);
}
