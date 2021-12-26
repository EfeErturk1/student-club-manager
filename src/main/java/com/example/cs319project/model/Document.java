package com.example.cs319project.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int documentId;

    @Column(nullable = true, length = 64)
    private String document_name;

    /*@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "file", referencedColumnName = "id")
    private FileDB document_file;*/

    private Date submission_date;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name="student_id", nullable=false)
    private Student author;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name="for_assignment",nullable=true)
    private Assignment belongsToAssignment;

}