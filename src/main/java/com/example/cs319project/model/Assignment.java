package com.example.cs319project.model;

import com.example.cs319project.model.clubstrategy.ClubRole;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
public class Assignment implements Comparable<Assignment> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int assignmentId;

    @Column(nullable = true, length = 64)
    private Date due_date;

    private String name;

    private String description;

    private String submissionDes;

    private int clubId;

    private boolean status; // 0 if non-complete, 1 if complete

    @JsonBackReference
    @ManyToMany
    @JoinTable(
            name = "assignees",
            joinColumns = @JoinColumn(name = "assignmentId"),
            inverseJoinColumns = @JoinColumn(name = "student_id"))
    Set<Student> assignees;

    private String assignmentFile;

    @OneToMany(mappedBy="belongsToAssignment", cascade = CascadeType.REMOVE)
    private Set<Document> documents;

    @Override
    public int compareTo(Assignment o) {
        return assignmentId - o.getAssignmentId();
    }
}