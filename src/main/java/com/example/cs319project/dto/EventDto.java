package com.example.cs319project.dto;

import com.example.cs319project.model.Student;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
public class EventDto {

    protected int id;

    private String photos;

    private String name;

    private int clubId;

    private int ge250;

    private String description;

    private String eventDate;

    private String eventFinish;

    private int duration;

    private int quota;

    private int remainingQuota;

    private String status;

    Set<Integer> participants;

}
