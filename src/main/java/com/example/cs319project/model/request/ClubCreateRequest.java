package com.example.cs319project.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClubCreateRequest {
    private String name;
    private String description;
    private int clubAdvisorId;
}
