package com.example.cs319project.model;

import com.example.cs319project.file.FileDB;
import com.example.cs319project.model.clubstrategy.ClubRole;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
public class Student {
    @Id
    public int id;

    public String name;

    private String photo;

    private int ge250;

    @OneToMany(mappedBy="student", cascade = CascadeType.REMOVE)
    private Set<ClubRole> rolesOfStudent;

    @JsonManagedReference
    @ManyToMany(mappedBy = "participants")
    Set<Event> joinedEvents;

    @JsonManagedReference
    @ManyToMany(mappedBy = "assignees")
    Set<Assignment> assignments;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "profile_photo", referencedColumnName = "id")
    private FileDB profilePhoto;

}
