package com.example.cs319project.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.List;

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

    private int quota;

    private int remainingQuota = quota;

    private int eventIdStore;

    //private List<Integer> participants;

}