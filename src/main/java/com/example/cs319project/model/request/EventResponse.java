package com.example.cs319project.model.request;

import com.example.cs319project.model.Student;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.util.Set;

@Getter
@Setter
@Builder
public class EventResponse {
    private int eventId;

    private String status;

    private String clubName;

    private String photos;

    private String name;

    private int clubId;

    private String description;

    private String eventDate;

    private String eventFinish;

    private int quota;

    private int remainingQuota;

    private int ge250;

    Set<Student> participants;
}
