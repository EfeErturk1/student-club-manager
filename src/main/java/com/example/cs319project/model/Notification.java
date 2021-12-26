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
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int notificationId;

    @Column(nullable = true, length = 64)
    private String date;

    private String description;

    private int clubId;

    private boolean isRequest;

    @JsonBackReference
    @ManyToMany
    @JoinTable(
            name = "notified_people",
            joinColumns = @JoinColumn(name = "notificationId"),
            inverseJoinColumns = @JoinColumn(name = "student_id"))
    Set<Student> notified_people;

    @JsonBackReference
    @ManyToMany
    @JoinTable(
            name = "notified_clubs",
            joinColumns = @JoinColumn(name = "notification_id"),
            inverseJoinColumns = @JoinColumn(name = "club_id"))
    Set<Club> notified_clubs;

    private String name;
}