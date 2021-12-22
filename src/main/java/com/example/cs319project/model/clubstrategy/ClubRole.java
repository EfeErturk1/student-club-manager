package com.example.cs319project.model.clubstrategy;

import com.example.cs319project.model.Club;
import com.example.cs319project.model.Student;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
public class ClubRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private ClubRoleName name;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name="club_id",nullable=false)
    private Club club;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name="student_id", nullable=false)
    private Student student;
}
