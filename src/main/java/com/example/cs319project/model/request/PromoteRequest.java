package com.example.cs319project.model.request;

import com.example.cs319project.model.Club;
import com.example.cs319project.model.Student;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PromoteRequest {
    private Student student;
    private Club club;
}
