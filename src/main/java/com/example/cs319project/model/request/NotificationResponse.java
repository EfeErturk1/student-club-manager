package com.example.cs319project.model.request;

import com.example.cs319project.model.Club;
import com.example.cs319project.model.Document;
import com.example.cs319project.model.Student;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@Builder
public class NotificationResponse {
    protected int notificationId;

    private String date;

    private String description;

    private int clubId;

    private boolean isRequest;

    Set<Student> notified_people;

    Set<Club> notified_clubs;
}
