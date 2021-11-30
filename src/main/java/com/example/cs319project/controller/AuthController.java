package com.example.cs319project.controller;

import com.example.cs319project.model.Role;
import com.example.cs319project.model.User;
import com.example.cs319project.model.request.LoginRequest;
import com.example.cs319project.model.request.MessageResponse;
import com.example.cs319project.model.request.SignupRequest;
import com.example.cs319project.service.RoleService;
import com.example.cs319project.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Set;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final RoleService roleService;
    //private final AuthenticationManager authenticationManager;
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

    @CrossOrigin
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody LoginRequest loginRequest){
        System.out.println('X');
        if(!userService.existsByEmail(loginRequest.getEmail())){
            return ResponseEntity.ok(new MessageResponse("Please sign up first"));
        }

        User loggedInUser = userService.findByEmail(loginRequest.getEmail());
        System.out.println(loggedInUser.getPassword());
        System.out.println(encoder.encode(loginRequest.getPassword()));
        if(!encoder.matches(loginRequest.getPassword(), loggedInUser.getPassword())){
            return ResponseEntity.ok(new MessageResponse("Wrong password"));
        }

        /*Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = "";
        if (principal instanceof UserDetails) {
             username = ((UserDetails)principal).getUsername();
        } else {
             username = principal.toString();
        }
        System.out.println(username);*/
        /*UsernamePasswordAuthenticationToken authReq
                = new UsernamePasswordAuthenticationToken(loggedInUser.getEmail(), loggedInUser.getPassword());
        Authentication auth = authenticationManager.authenticate(authReq);
        SecurityContext sc = SecurityContextHolder.getContext();
        sc.setAuthentication(auth);*/
        return ResponseEntity.ok(new MessageResponse("Logged in"));
    }


}
