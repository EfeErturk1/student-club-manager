package com.example.cs319project.controller;

import com.example.cs319project.model.Advisor;
import com.example.cs319project.model.Club;
import com.example.cs319project.model.Event;
import com.example.cs319project.model.Student;
import com.example.cs319project.model.clubstrategy.ClubRole;
import com.example.cs319project.model.clubstrategy.ClubRoleName;
import com.example.cs319project.model.request.*;
import com.example.cs319project.service.AdvisorService;
import com.example.cs319project.service.ClubRoleService;
import com.example.cs319project.service.ClubService;
import com.example.cs319project.service.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/club")
public class ClubController {

    private final ClubService clubService;
    private final ClubRoleService clubRoleService;
    private final StudentService studentService;
    private final AdvisorService advisorService;

    @PostMapping(value = "/addClub")
    public ResponseEntity<?> addClub(@Valid @RequestBody ClubCreateRequest clubRequest){
        Club club = Club.builder().name(clubRequest.getName()).description(clubRequest.getDescription()).build();
        if(clubRequest.getClubAdvisorId() != 0){
            Advisor advisor = advisorService.findById(clubRequest.getClubAdvisorId());
            advisor.setClub(club);
            advisorService.createNewAdvisor(advisor);
        }

        clubService.createNewClub(club);
        return ResponseEntity.ok(new MessageResponse("Club added successfully!"));
    }

    @PostMapping(value = "/joinClub")
    public ResponseEntity<?>  joinClub(@Valid @RequestBody JoinClubRequest request){
        if(studentService.findById(request.getStudentId()) == null){
            return ResponseEntity.ok(new MessageResponse("Student doesnot exists"));
        }

        if(clubService.findById(request.getClubId()) == null){
            return ResponseEntity.ok(new MessageResponse("Club doesnot exists"));
        }

        // if both of them exists we can try to join to club
        int studentId = request.getStudentId();
        int clubId = request.getClubId();
        List<ClubRole> clubRoles = clubRoleService.findByStudentId(studentId);
        List<Integer> registeredClubs = new ArrayList<>();
        for(ClubRole role: clubRoles){
            registeredClubs.add(role.getClub().getId());
        }

        if(clubRoles.size() == 0 || !registeredClubs.contains(clubId)){
            Club registeredClub = clubService.findById(clubId);
            Student registeringStudent = studentService.findById(studentId);
            ClubRole clubRole = ClubRole.builder().name(ClubRoleName.MEMBER).student(registeringStudent).club(registeredClub).build();
            clubRoleService.assignNewRole(clubRole);
            return ResponseEntity.ok(new MessageResponse("Student successfully become a member"));
        }
        else{
            return ResponseEntity.ok(new MessageResponse("You have already registered"));
        }
    }

    @PostMapping(value = "/deleteClub")
    public ResponseEntity<?> deleteClub(@Valid @RequestBody DeleteClubRequest deleteClubRequest){
        /*
        Club club = clubService.findById(idHolder.getId());
        Advisor advisor = advisorService.findByClub(club);
        if(advisor != null){
            advisor.setClub(null);
        }
        */
        clubService.deleteClub(clubService.findById(deleteClubRequest.getClubId()));
        return ResponseEntity.ok(new MessageResponse("Club has deleted successfully!"));
    }


    @GetMapping(value = "/allClubs", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<List<Club>> allClubs(){
        return ResponseEntity.ok(clubService.findAll());
    }

    @GetMapping(value= "/clubView", produces =  MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<Club> getSpecificClub(@Valid @RequestBody IdHolder idHolder) {
        Club club = clubService.findById(idHolder.getId());
        return ResponseEntity.ok(club);
    }
}
