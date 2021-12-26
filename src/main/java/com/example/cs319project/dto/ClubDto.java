package com.example.cs319project.dto;

import com.example.cs319project.model.clubstrategy.ClubRole;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

//DTO Design Pattern

@Getter
@Setter
@AllArgsConstructor
public class ClubDto {
    private int id;

    private String name;

    private String description;

    private String photos;

    private Set<Integer> roles;

}
