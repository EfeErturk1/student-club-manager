package com.example.cs319project.model.request;

import com.example.cs319project.model.clubstrategy.ClubRole;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Builder
public class ClubResponse {
    private int id;

    private String photos;

    private String name;

    private String description;

    private Set<ClubRoleResponse> roles;

    private int numberOfEvents;
}
