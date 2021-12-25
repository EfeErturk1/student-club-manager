package com.example.cs319project.model.request;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class NotificationCreateRequest {
    private String date;
    private String description;
    private int clubId;
    private boolean isRequest;
    private List<Integer> notified_people;
}
