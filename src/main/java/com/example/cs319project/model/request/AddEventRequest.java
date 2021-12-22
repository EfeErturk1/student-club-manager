package com.example.cs319project.model.request;

import com.example.cs319project.model.Club;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddEventRequest {
    private String name;
    private String description;
    private int clubId;
    private int quota;
    private String eventDate;
    private int duration;
}