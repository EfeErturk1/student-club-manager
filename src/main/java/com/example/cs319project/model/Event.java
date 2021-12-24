package com.example.cs319project.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.List;
import java.util.*;
import java.text.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int eventId;

    @Column(nullable = true, length = 64)
    private String photos;

    private String name;

    private int clubId;

    private String description;

    private String eventDate;

    private String eventFinish;

    //String oldstring = "2011-01-18 00:00:00.0";
    //Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").parse(oldstring);

    private int quota;

    private int remainingQuota;

    private int ge250;

    @Column(columnDefinition = "VARCHAR(60) CHECK (status IN ('REJECTED', 'ACCEPTED', 'NOT_DECIDED'))")
    private String status;

    @JsonBackReference
    @ManyToMany
    @JoinTable(
            name = "event_participants",
            joinColumns = @JoinColumn(name = "event_eventId"),
            inverseJoinColumns = @JoinColumn(name = "student_id"))
    Set<Student> participants;


}