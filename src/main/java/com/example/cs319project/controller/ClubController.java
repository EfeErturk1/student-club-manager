package com.example.cs319project.controller;

import com.example.cs319project.dto.ClubDto;
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
            if(advisor.getClub() != null){
                return ResponseEntity.ok(new MessageResponse("Advisor already have a club first try to remove advisor's club!"));
            }
            advisor.setClub(club);
            advisorService.createNewAdvisor(advisor); //it calls save method of repository so it updates as well
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
    public ResponseEntity<?> deleteClub(@Valid @RequestBody IdHolder idHolder){

        Club club = clubService.findById(idHolder.getId());
        if(club == null){
            return ResponseEntity.ok(new MessageResponse("There is no such a club"));
        }

        // if club exists check for its advisor
        Advisor advisor = advisorService.findByClub(club);
        if(advisor != null){
            advisor.setClub(null);
        }

        clubService.deleteClub(clubService.findById(idHolder.getId()));
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

    @GetMapping(value = "/getStudentClub", produces =  MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<List<Club>> getClubOfStudent(@Valid @RequestBody IdHolder idHolder) {
        List<ClubRole> clubRoles = clubRoleService.findByStudentId(idHolder.getId());
        List<Club> clubsOfStudent = new ArrayList<>();
        for(ClubRole role: clubRoles){
            clubsOfStudent.add(clubService.findById(role.getClub().getId()));
        }
        return ResponseEntity.ok(clubsOfStudent);
    }

    //@PreAuthorize(value = "hasRole('ROLE_ADMIN')") buraya en son bu tarz şeyler gelecek güvenlik için
    @PostMapping(value = "/assignPresident", produces =  MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> assignPresident(@Valid @RequestBody JoinClubRequest idHolder){
        Club club = clubService.findById(idHolder.getClubId());
        Student student = studentService.findById(idHolder.getStudentId());

        if(student == null){
            return ResponseEntity.ok(new MessageResponse("There is no such student exists"));
        }

        if(club == null){
            return ResponseEntity.ok(new MessageResponse("There is no such club exists"));
        }

        // if club exists check for its president
        List<ClubRole> clubRoles = clubRoleService.findByClubId(idHolder.getClubId());
        for(ClubRole role: clubRoles){
            if(role.getName() == ClubRoleName.PRESIDENT){
                return ResponseEntity.ok(new MessageResponse("This club already has an president "));
            }
        }

        List<ClubRole> studentRoles = clubRoleService.findByStudentId(idHolder.getStudentId());
        for(ClubRole role: studentRoles){
            if(role.getClub().getId() == idHolder.getClubId()){
                role.setName(ClubRoleName.PRESIDENT);
                clubRoleService.assignNewRole(role);
                return ResponseEntity.ok(new MessageResponse("Member has become a president"));
            }
        }

        ClubRole newRole = ClubRole.builder().student(student).club(club).name(ClubRoleName.PRESIDENT).build();
        clubRoleService.assignNewRole(newRole);
        return ResponseEntity.ok(new MessageResponse("Club has a president now"));
    }

    @PostMapping(value = "/updateClub", produces =  MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateClub(@Valid @RequestBody ClubDto dto) {
        clubService.updateClub(dto);
        return ResponseEntity.ok(new MessageResponse("Club has been updated"));
    }
   }
