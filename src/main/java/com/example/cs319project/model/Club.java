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
public class Club {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int id;

    @Column(nullable = true, length = 64)
    private String photos;

    private String name;

    private String description;

    @OneToMany(mappedBy="club")
    private Set<ClubRole> roles;

}
