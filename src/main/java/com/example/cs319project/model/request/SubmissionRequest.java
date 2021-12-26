package com.example.cs319project.model.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SubmissionRequest {
    private int id;
    private String description;
}
