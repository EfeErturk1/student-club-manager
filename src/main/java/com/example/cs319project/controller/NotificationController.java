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

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/notification")
public class NotificationController {

    private final ClubService clubService;
    private final ClubRoleService clubRoleService;
    private final NotificationService notificationService;
    private final StudentService studentService;


    @PostMapping(value = "/addNotification")
    public ResponseEntity<?> addNotification(@Valid @RequestBody NotificationCreateRequest request) {
        Set<Student> notifieds = new HashSet<>();

        for(int studentId: request.getNotified_people()){
            Student student = studentService.findById(studentId);
            notifieds.add(student);
        }

        Notification notification = Notification.builder().date(request.getDate()).description(request.getDescription()).clubId(request.getClubId()).isRequest(request.isRequest()).notified_people(notifieds).build();
        notificationService.createNewNotification(notification);
        return ResponseEntity.ok(new MessageResponse("Notification added successfully!"));
    }

    @PostMapping(value = "/deleteNotification")
    public ResponseEntity<?> deleteNotification(@Valid @RequestBody IdHolder id) {
        notificationService.deleteNotification(notificationService.findByNotificationId(id.getId()));
        return ResponseEntity.ok(new MessageResponse("Notification deleted successfully!"));
    }


    @GetMapping(value = "/allNotification", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<List<Notification>> all(){
        return ResponseEntity.ok(notificationService.findAll());
    }

    @GetMapping(value = "/getNotification")
    public @ResponseBody ResponseEntity<Notification> getNotification(@Valid @RequestBody IdHolder id) {
        return ResponseEntity.ok(notificationService.findByNotificationId(id.getId()));
    }
}

