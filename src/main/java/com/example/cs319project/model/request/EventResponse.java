package com.example.cs319project.model.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;

@Getter
@Setter
@Builder
public class EventResponse {
    private int eventId;

    private String status;
}
