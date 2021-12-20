package com.example.cs319project.model.clubstrategy;

import com.example.cs319project.model.Club;
import com.example.cs319project.model.Student;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

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
    @JoinColumn(name="club_id",nullable=false)
    private Club club;

    @ManyToOne
    @JoinColumn(name="student_id", nullable=false)
    private Student student;
}
