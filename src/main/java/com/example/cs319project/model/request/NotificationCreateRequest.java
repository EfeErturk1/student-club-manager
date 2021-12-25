package com.example.cs319project.model.request;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
public class NotificationCreateRequest {
    private Date date;
    private String description;
    private int clubId;
    private boolean isRequest;
}
