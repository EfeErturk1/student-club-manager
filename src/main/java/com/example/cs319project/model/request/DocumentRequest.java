package com.example.cs319project.model.request;

import com.example.cs319project.file.FileDB;
import com.example.cs319project.model.Assignment;
import com.example.cs319project.model.Student;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocumentRequest {
    protected int documentId;

    private String document_name;

    private Date submission_date;

    private int author;

    private int assignment_id;
}
