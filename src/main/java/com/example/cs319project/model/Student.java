package com.example.cs319project.model;

//import com.example.cs319project.file.FileDB;
import com.example.cs319project.model.clubstrategy.ClubRole;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.aspectj.weaver.ast.Not;

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

    private int ge250;

    @OneToMany(mappedBy="student", cascade = CascadeType.REMOVE)
    private Set<ClubRole> rolesOfStudent;

    @JsonManagedReference
    @ManyToMany(mappedBy = "participants", cascade = CascadeType.ALL)
    Set<Event> joinedEvents;

    @JsonManagedReference
    @ManyToMany(mappedBy = "assignees")
    Set<Assignment> assignments;


    @JsonManagedReference
    @ManyToMany(mappedBy = "notified_people")
    Set<Notification> notifications;


    private String profilePhotoName;

    @OneToMany(mappedBy="author", cascade = CascadeType.REMOVE)
    private Set<Document> documentFilledByStudent;

}
