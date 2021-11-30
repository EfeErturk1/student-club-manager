package com.example.cs319project.controller;

import com.example.cs319project.model.Role;
import com.example.cs319project.model.User;
import com.example.cs319project.model.request.MessageResponse;
import com.example.cs319project.model.request.SignupRequest;
import com.example.cs319project.service.RoleService;
import com.example.cs319project.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder encoder;

    @CrossOrigin
    @PostMapping("/signupStudent")
    public ResponseEntity<?> registerStudent(@Valid @RequestBody SignupRequest signUpRequest) {
        if(userService.existsByEmail(signUpRequest.getEmail())){
            return ResponseEntity.badRequest().body("Error: Email is already taken!");
        }

        if(userService.existsByName(signUpRequest.getName())){
            return ResponseEntity.badRequest().body(new MessageResponse("Error: name is already taken"));
        }

        //creating a new user
        Role role = roleService.findByName("STUDENT");
        User user = User.builder().role(role).username(signUpRequest.getName()).email(signUpRequest.getEmail()).password(encoder.encode(signUpRequest.getPassword())).build();
        userService.createNewUser(user);
        return ResponseEntity.ok(new MessageResponse("Student registered succesfully"));
    }
}
