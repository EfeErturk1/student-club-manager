package com.example.cs319project.controller;

import com.example.cs319project.model.*;
import com.example.cs319project.model.request.*;
import com.example.cs319project.security.JwtUtils;
import com.example.cs319project.security.MyUserDetails;
import com.example.cs319project.service.*;
import io.jsonwebtoken.Jwt;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

    private final AuthenticationManager authenticationManager;

    private final UserService userService;

    private final RoleService roleService;

    private final StudentService studentService;

    private final AdminService adminService;

    private final PasswordEncoder encoder;

    private final JwtUtils jwtUtils;

    private final AdvisorService advisorService;

    private final ClubService clubService;

    @PostMapping("/login")
    public JwtResponse authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority()).collect(Collectors.toList());

        // finding club
        Club club = null;
        if(advisorService.findById(userDetails.getId()) != null){
             club = advisorService.findById(userDetails.getId()).getClub();
        }
        JwtResponse response = JwtResponse
                .builder()
                .token(jwt)
                .id(userDetails.getId())
                .username(userDetails.getUsername())
                .email(userDetails.getEmail())
                .roles(roles)
                .build();

        if(club != null){
            response.setClubId(club.getId());
            return response;
        }
            return response;


    }

    @PostMapping("/signupStudent")
    public ResponseEntity<?> registerStudent(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userService.existsByName(signUpRequest.getName())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userService.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account

        User user = User
                .builder()
                .name(signUpRequest.getName())
                .email(signUpRequest.getEmail())
                .photo(signUpRequest.getPhoto())
                .ge250(0)
                .password(encoder.encode(signUpRequest.getPassword()))
                .build();

        Role role = roleService.findByName(RoleType.ROLE_STUDENT);
        user.setRole(role);
        userService.createNewUser(user);
        Student student = new Student();
        student.setId(user.getId());
        student.setName(user.getName());
        studentService.createNewStudent(student);

        return ResponseEntity.ok(new MessageResponse("Student registered successfully!"));
    }

    @PostMapping("/createAdvisor")
    public ResponseEntity<?> registerAdvisor(@Valid @RequestBody AdvisorCreateRequest request) {
        if (userService.existsByName(request.getName())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userService.existsByEmail(request.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
        }

        User user = User
                .builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(encoder.encode(request.getPassword()))
                .build();

        Role role = roleService.findByName(RoleType.ROLE_ADVISOR);
        user.setRole(role);
        userService.createNewUser(user);
        Advisor advisor = new Advisor();
        advisor.setName(user.getName());
        advisor.setId(user.getId());
        Club club = clubService.findById(request.getClubId());
        advisor.setClub(club);
        advisorService.createNewAdvisor(advisor);
        return ResponseEntity.ok(new MessageResponse("Advisor created successfully!"));

    }

    @PostMapping("/createAdmin")
    public ResponseEntity<?> registerAdmin(@Valid @RequestBody AdminCreateRequest request) {
        if (userService.existsByName(request.getName())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userService.existsByEmail(request.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
        }

        User user = User
                .builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(encoder.encode(request.getPassword()))
                .build();

        Role role = roleService.findByName(RoleType.ROLE_ADMIN);
        user.setRole(role);
        userService.createNewUser(user);
        Admin admin = new Admin();
        admin.setName(user.getName());
        admin.setId(user.getId());
        adminService.createNewAdmin(admin);
        return ResponseEntity.ok(new MessageResponse("Admin created successfully!"));

    }

    @PostMapping(value = "/deleteStudent")
    public ResponseEntity<?> deleteStudent(@Valid @RequestBody IdHolder idHolder) {
        if (studentService.findById(idHolder.getId()) == null) {
            return ResponseEntity.ok(new MessageResponse("Student doesnot exists"));
        }

        if(!((studentService.findById(idHolder.getId()).getRolesOfStudent() == null) || (studentService.findById(idHolder.getId()).getRolesOfStudent().size() == 0))){
            return ResponseEntity.ok(new MessageResponse("To delete, firstly you should leave all the clubs that you are a member of!"));
        }

        if(!((studentService.findById(idHolder.getId()).getJoinedEvents() == null) || (studentService.findById(idHolder.getId()).getJoinedEvents().size() == 0))){
            return ResponseEntity.ok(new MessageResponse("To delete, firstly you should leave all the events you joined!"));
        }
        studentService.findById(idHolder.getId()).setJoinedEvents(null);
        studentService.findById(idHolder.getId()).setRolesOfStudent(null);
        studentService.findById(idHolder.getId()).setAssignments(null);
        studentService.deleteStudent(studentService.findById(idHolder.getId()));
        userService.deleteUser(userService.findById(idHolder.getId()));
        return ResponseEntity.ok(new MessageResponse("Student deleted successfully!"));
    }

    }
