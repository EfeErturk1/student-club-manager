package com.example.cs319project.model.request;

import lombok.*;

import java.util.List;

//Response that returns the authentication information of logged in user

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse {

    public String token;

    @Builder.Default
    private String type = "Bearer";

    private Integer id;

    private String username;

    private String email;

    private List<String> roles;

    private int clubId;
}
