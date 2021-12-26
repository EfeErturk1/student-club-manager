package com.example.cs319project.dto;

import com.example.cs319project.model.Document;
import com.example.cs319project.model.Student;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
public class AssignmentDto {
    private Date due_date;
    private String name;
    private String description;
    private int clubId;
    List<Integer> assignees;
    private List<Integer> documents;
}
