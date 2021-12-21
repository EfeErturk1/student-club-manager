package com.example.cs319project.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JoinEventRequest {
    private int studentId;
    private int eventId;
}
