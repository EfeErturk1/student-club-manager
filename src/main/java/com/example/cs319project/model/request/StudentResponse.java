package com.example.cs319project.model.request;

import com.example.cs319project.file.FileDB;
import com.example.cs319project.model.Assignment;
import com.example.cs319project.model.Event;
import com.example.cs319project.model.clubstrategy.ClubRole;
import com.example.cs319project.model.clubstrategy.ClubRoleName;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
public class StudentResponse {
    public int id;

    public String name;

    private int ge250;

    private ClubRoleName role;
}
