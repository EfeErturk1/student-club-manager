package com.example.cs319project.model;

import com.example.cs319project.model.clubstrategy.ClubRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
public class Advisor {
    @Id
    public int id;

    public String name;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "club_id")
    private Club club;

}
