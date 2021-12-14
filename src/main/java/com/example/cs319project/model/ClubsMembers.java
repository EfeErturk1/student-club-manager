package com.example.cs319project.model;

import javax.persistence.*;

@Entity
public class ClubsMembers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    Student student;

    @ManyToOne
    @JoinColumn(name = "club_id")
    Club club;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, unique = true, nullable = false)
    private ClubRoleNames name;

}
