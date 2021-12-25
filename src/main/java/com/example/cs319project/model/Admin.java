package com.example.cs319project.model;

import com.example.cs319project.model.clubstrategy.ClubRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Subselect;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Subselect("select 'admin' as name")
public class Admin {
    @Id
    public int id;

    public String name;
}