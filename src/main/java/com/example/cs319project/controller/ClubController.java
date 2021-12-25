package com.example.cs319project.controller;

import com.example.cs319project.dto.ClubDto;
import com.example.cs319project.model.*;
import com.example.cs319project.model.clubstrategy.ClubRole;
import com.example.cs319project.model.clubstrategy.ClubRoleName;
import com.example.cs319project.model.request.*;
import com.example.cs319project.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/club")
public class ClubController {

    private final ClubService clubService;
    private final ClubRoleService clubRoleService;
    private final StudentService studentService;
    private final AdvisorService advisorService;
    private final EventService eventService;
    private final AssignmentService assignmentService;

    @PostMapping(value = "/addClub")
    public ResponseEntity<?> addClub(@Valid @RequestBody ClubCreateRequest clubRequest){
        Club club = Club.builder().name(clubRequest.getName()).description(clubRequest.getDescription()).photos(clubRequest.getPhoto()).advisor(advisorService.findById(clubRequest.getClubAdvisorId())).build();
        if(clubRequest.getClubAdvisorId() != 0){
            Advisor advisor = advisorService.findById(clubRequest.getClubAdvisorId());
            if(advisor.getClub() != null){
                return ResponseEntity.ok(new MessageResponse("Advisor already have a club first try to remove advisor's club!"));
            }
            advisor.setClub(club);
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
            clubRoleService.promote(clubRole);

            List<Assignment> assignments = assignmentService.findAll();
            for(Assignment assignment: assignments){
                if(assignment.getClubId() == clubId){
                    Set<Student> students = assignment.getAssignees();
                    students.add(registeringStudent);
                    assignment.setAssignees(students);
                    assignmentService.saveAssignment(assignment);
                }
            }
            return ResponseEntity.ok(new MessageResponse("Student successfully become a member"));
        }
        else{
            return ResponseEntity.ok(new MessageResponse("You have already registered"));
        }
    }

    @PostMapping(value = "/leaveClub")
    public ResponseEntity<?>  leaveClub(@Valid @RequestBody JoinClubRequest request){
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
            return ResponseEntity.ok(new MessageResponse("You are not a member of the club"));
        }
        else{
            Club registeredClub = clubService.findById(clubId);
            Student registeringStudent = studentService.findById(studentId);
            List<ClubRole> studentRoles = clubRoleService.findByStudentId(registeringStudent.getId());
            for(ClubRole role: studentRoles){
                if(role.getClub().getId() == registeredClub.getId()){
                    clubRoleService.deleteRole(role);

                    //deleting from assignees
                    List<Assignment> allAssignments  = assignmentService.findAll();
                    List<Assignment> clubAssignments = new ArrayList<>();

                    for(Assignment assigment: allAssignments){
                        if(assigment.getClubId() == clubId){
                            clubAssignments.add(assigment);
                        }
                    }

                    for(Assignment assignment: clubAssignments){
                        Set<Student> assigned = assignment.getAssignees();
                        assigned.remove(registeringStudent);
                        assignment.setAssignees(assigned);
                        assignmentService.saveAssignment(assignment);
                    }

                    return ResponseEntity.ok(new MessageResponse("You left the club"));
                }
            }
            return ResponseEntity.ok(new MessageResponse("You are not in the club"));
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
    public @ResponseBody ResponseEntity<List<ClubResponse>> allClubs(){
        List<Club> allClubs = clubService.findAll();
        List<ClubResponse> clubs = new ArrayList<>();

        for(Club club: allClubs){
            int eventNumber = (int) eventService.findNumberOfEventsOfClub(club);
            Set<ClubRole> roles = club.getRoles();
            Set<ClubRoleResponse> rolesOfClub = new HashSet<>();

            for(ClubRole role: roles){
                ClubRoleResponse roleResponse = ClubRoleResponse.builder().clubId(club.getId()).studentId(role.getStudent().getId()).id(role.getId()).name(role.getName()).build();
                rolesOfClub.add(roleResponse);
            }

            ClubResponse response = ClubResponse.builder().name(club.getName()).
                    photos(club.getPhotos()).description(club.getDescription()).
                    id(club.getId()).numberOfEvents(eventNumber).roles(rolesOfClub).build();
            clubs.add(response);
        }
        return ResponseEntity.ok(clubs);
    }

    @GetMapping(value= "/clubView", produces =  MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<List<ClubResponse>> getSpecificClub(@RequestParam(name = "id") int idHolder) {
        List<ClubResponse> clubs = new ArrayList<>();
        Club club = clubService.findById(idHolder);
        int eventNumber = (int) eventService.findNumberOfEventsOfClub(club);
        Set<ClubRole> roles = club.getRoles();
        Set<ClubRoleResponse> rolesOfClub = new HashSet<>();

        for(ClubRole role: roles){
            ClubRoleResponse roleResponse = ClubRoleResponse.builder().clubId(club.getId()).studentId(role.getStudent().getId()).id(role.getId()).name(role.getName()).build();
            rolesOfClub.add(roleResponse);
        }

        ClubResponse response = ClubResponse.builder().name(club.getName()).
                photos(club.getPhotos()).description(club.getDescription()).
                id(club.getId()).numberOfEvents(eventNumber).roles(rolesOfClub).build();
        clubs.add(response);

        return ResponseEntity.ok(clubs);
    }

    @GetMapping(value = "/getStudentClub", produces =  MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<List<Club>> getClubOfStudent(@RequestParam(name = "id") int  idHolder) {
        List<ClubRole> clubRoles = clubRoleService.findByStudentId(idHolder);
        List<ClubResponse> clubs = new ArrayList<>();
        List<Club> clubsOfStudent = new ArrayList<>();
        Set<ClubRoleResponse> rolesOfClub = new HashSet<>();
        for(ClubRole role: clubRoles){
            int eventNumber = (int) eventService.findNumberOfEventsOfClub(clubService.findById(role.getClub().getId()));
            ClubResponse response = ClubResponse.builder().name((clubService.findById(role.getClub().getId())).getName()).
                    photos((clubService.findById(role.getClub().getId())).getPhotos()).description((clubService.findById(role.getClub().getId())).getDescription()).
                    id((clubService.findById(role.getClub().getId())).getId()).numberOfEvents(eventNumber).roles(rolesOfClub).build();
            clubs.add(response);
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
                clubRoleService.promote(role);
                return ResponseEntity.ok(new MessageResponse("Member has become a president"));
            }
        }

        ClubRole newRole = ClubRole.builder().student(student).club(club).name(ClubRoleName.PRESIDENT).build();
        clubRoleService.promote(newRole);
        return ResponseEntity.ok(new MessageResponse("Club has a president now"));
    }

    @PostMapping(value = "/updateClub", produces =  MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateClub(@Valid @RequestBody ClubDto dto) {
        clubService.updateClub(dto);
        return ResponseEntity.ok(new MessageResponse("Club has been updated"));
    }

    @GetMapping(value = "/getRolesOfClub",  produces =  MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getClubRoles(@RequestParam(name = "id") int idHolder){
        List<ClubRole> clubRoles = clubRoleService.findByClubId(idHolder);
        return ResponseEntity.ok(clubRoles);
    }

    @GetMapping(value = "/getNameOfClub",  produces =  MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getNameOfClub(@RequestParam(name = "id") int idHolder){
        return ResponseEntity.ok(clubService.findById(idHolder).getName());
    }

    @GetMapping(value = "/getMembersOfClub",  produces =  MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<StudentResponse>> getMembers(@RequestParam(name = "id") int idHolder){
        Club club = clubService.findById(idHolder);
        List<StudentResponse> studentResponses = new ArrayList<>();

        if(club == null){
            return ResponseEntity.ok(null);
        }
        List<ClubRole> clubRoles = clubRoleService.findByClubId(idHolder);

        for(ClubRole role: clubRoles){
            Student student = studentService.findById(role.getStudent().getId());
            StudentResponse response = StudentResponse.builder().name(student.getName()).id(student.getId()).role(role.getName()).ge250(student.getGe250()).build();
            studentResponses.add(response);
        }

        return ResponseEntity.ok(studentResponses);
    }

    @GetMapping(value = "/getMembersIdsOfClub",  produces =  MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getMembersIds(@RequestParam(name = "id") int idHolder){
        Club club = clubService.findById(idHolder);
        List<Integer> studentIds = new ArrayList<>();

        if(club == null){
            return ResponseEntity.ok(null);
        }
        List<ClubRole> clubRoles = clubRoleService.findByClubId(idHolder);

        for(ClubRole role: clubRoles){
            studentIds.add(role.getStudent().getId());
        }

        return ResponseEntity.ok(studentIds);
    }

    }
