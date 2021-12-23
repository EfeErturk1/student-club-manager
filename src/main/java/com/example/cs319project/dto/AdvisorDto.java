package com.example.cs319project.dto;

import com.example.cs319project.model.Club;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Getter
@Setter
@AllArgsConstructor
public class AdvisorDto {
    public int id;
    public String name;
    private int club;
}
