package com.example.cs319project.model;

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

    @OneToMany(mappedBy="student")
    private Set<ClubRole> rolesOfStudent;

    @JsonManagedReference
    @ManyToMany(mappedBy = "participants")
    Set<Event> joinedEvents;

}
