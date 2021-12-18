package com.example.cs319project.model;

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
    protected int id;

    public String name;

    @OneToMany(mappedBy = "student")
    Set<ClubsMembers> memberedClubs;
}
