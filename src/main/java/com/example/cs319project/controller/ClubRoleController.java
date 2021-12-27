package com.example.cs319project.controller;


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

//This is the controller to manage the relation between clubs and members

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/clubRole")
public class ClubRoleController {

    private final ClubService clubService;
    private final ClubRoleService clubRoleService;
    private final ClubController clubController;
    private final StudentService studentService;
    private final DocumentService documentService;
    private final NotificationService notificationService;

    /*Role hierarchy
    MEMBER -> ACTIVE_MEMBER
    ACTIVE_MEMBER -> BOARD_MEMBER
    BOARD_MEMBER -> PRESIDENT*/
    @PostMapping(value= "/promote")
    public ResponseEntity<?> promote(@Valid @RequestBody JoinClubRequest request) {
        // has fields student and club, promotes the students role in that club
        Student student = studentService.findById(request.getStudentId());
        Club club = clubService.findById(request.getClubId());
        ClubRole role = clubRoleService.findStudentsRoleInClub(request.getStudentId(),request.getClubId());

        if(student == null){
            return ResponseEntity.ok(new MessageResponse("There is no such student"));
        }

        if(club == null){
            return ResponseEntity.ok(new MessageResponse("There is no such club"));
        }

        if(role.getName() == ClubRoleName.BOARD_MEMBER){
            return clubController.assignPresident(request);
        }
        else{
            if(role.getName() == ClubRoleName.MEMBER)
                role.setName(ClubRoleName.ACTIVE_MEMBER);
            else if (role.getName() == ClubRoleName.ACTIVE_MEMBER)
                role.setName(ClubRoleName.BOARD_MEMBER);
            clubRoleService.promote(role);
            String str = "You have been promoted to " + role.getName() + " in the club " + club.getName();

            Set<Student> notifieds = new HashSet<>();
            notifieds.add(student);

            Notification notification = Notification.builder()
                    .date(null)
                    .description(str)
                    .clubId(club.getId())
                    .isRequest(false)
                    .name(club.getName())
                    .notified_people(notifieds).build();
            notificationService.createNewNotification(notification);
            return ResponseEntity.ok(new MessageResponse("Role has been updated"));
        }
    }

    /*Role hierarchy
    PRESIDENT -> BOARD_MEMBER
    BOARD_MEMBER -> ACTIVE_MEMBER
    ACTIVE_MEMBER -> MEMBER*/
    @PostMapping(value= "/demote")
    public ResponseEntity<?> demote(@Valid @RequestBody JoinClubRequest request) {
        // has fields student and club, promotes the students role in that club
        Student student = studentService.findById(request.getStudentId());
        Club club = clubService.findById(request.getClubId());
        ClubRole role = clubRoleService.findStudentsRoleInClub(request.getStudentId(),request.getClubId());

        if(student == null){
            return ResponseEntity.ok(new MessageResponse("There is no such student"));
        }

        if(club == null){
            return ResponseEntity.ok(new MessageResponse("There is no such club"));
        }

        if(role.getName() == ClubRoleName.ACTIVE_MEMBER)
            role.setName(ClubRoleName.MEMBER);
        else if (role.getName() == ClubRoleName.BOARD_MEMBER)
            role.setName(ClubRoleName.ACTIVE_MEMBER);
        else if (role.getName() == ClubRoleName.PRESIDENT)
            role.setName(ClubRoleName.BOARD_MEMBER);

        clubRoleService.demote(role);
        Set<Student> notifieds = new HashSet<>();
        notifieds.add(student);
        String str = "You have been demoted to " + role.getName() + " in the club " + club.getName();
        Notification notification = Notification.builder()
                .date(null)
                .description(str)
                .clubId(club.getId())
                .isRequest(false)
                .name(club.getName())
                .notified_people(notifieds).build();
        notificationService.createNewNotification(notification);
        return ResponseEntity.ok(new MessageResponse("Role has been updated"));
    }

    @GetMapping(value= "/fetchAllRolesByClubId", produces =  MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<List<ClubRole>> fetchAllRolesByClubId(@RequestParam(name = "id") int  idHolder) {
        List<ClubRole> clubRoles = clubRoleService.findByClubId(idHolder);
        return ResponseEntity.ok(clubRoles);
    }

    @GetMapping(value= "/fetchAllRolesByStudentId", produces =  MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<List<ClubRole>> fetchAllRolesByStudentId(@RequestParam(name = "id") int  idHolder) {
        List<ClubRole> clubRoles = clubRoleService.findByStudentId(idHolder);
        return ResponseEntity.ok(clubRoles);
    }
}

