package com.example.cs319project.model.request;

import com.example.cs319project.model.Document;
import com.example.cs319project.model.Student;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@Builder
public class AssignmentResponse {
    private int assignmentId;

    private Date due_date;

    private String name;

    private String description;

    private int clubId;

    private String clubName;

    private boolean status; // 0 if non-complete, 1 if complete

    Set<Student> assignees;

    private Set<Document> documents;
}
