package com.example.cs319project.model.request;

import com.example.cs319project.model.Club;
import com.example.cs319project.model.Student;
import com.example.cs319project.model.clubstrategy.ClubRoleName;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
@Getter
@Setter
@Builder
public class ClubRoleResponse {
    private int id;

    private ClubRoleName name;

    private int clubId;

    private int studentId;

}
