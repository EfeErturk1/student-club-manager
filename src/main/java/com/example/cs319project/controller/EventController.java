package com.example.cs319project.controller;


import com.example.cs319project.model.Advisor;
import com.example.cs319project.model.Club;
import com.example.cs319project.model.Event;
import com.example.cs319project.model.Student;
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
import java.util.List;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/event")
public class EventController {

    private final ClubService clubService;
    private final ClubRoleService clubRoleService;
    private final StudentService studentService;
    private final EventService eventService;


    @PostMapping(value = "/addEvent")
    public ResponseEntity<?> addEvent(@Valid @RequestBody AddEventRequest addEventRequest) {
        Event event = Event.builder().name(addEventRequest.getName()).description(addEventRequest.getDescription()).clubId(addEventRequest.getClubId()).quota(addEventRequest.getQuota()).remainingQuota(addEventRequest.getQuota()).eventDate(addEventRequest.getEventDate()).build();
        eventService.addEvent(event);
        return ResponseEntity.ok(new MessageResponse("Event added successfully!"));
    }

    @PostMapping(value = "/deleteEvent")
    public ResponseEntity<?> deleteEvent(@Valid @RequestBody IdHolder deleteEventRequest) {
        eventService.deleteEvent(eventService.findByEventId(deleteEventRequest.getId()));
        return ResponseEntity.ok(new MessageResponse("Event deleted successfully!"));
    }


    @PostMapping(value = "/joinEvent")
    public ResponseEntity<?> joinEvent(@Valid @RequestBody JoinEventRequest joinEventRequest) {
        if (studentService.findById(joinEventRequest.getStudentId()) == null) {
            return ResponseEntity.ok(new MessageResponse("Student doesnot exists"));
        }

        if (eventService.findByEventId(joinEventRequest.getEventId()) == null) {
            return ResponseEntity.ok(new MessageResponse("Event doesnot exists"));
        }


        int studentId = joinEventRequest.getStudentId();
        int eventId = joinEventRequest.getEventId();
        int clubId = eventService.findByEventId(eventId).getClubId();

        List<ClubRole> clubRoles = clubRoleService.findByStudentId(studentId);

        List<Integer> registeredClubs = new ArrayList<>();
        for (ClubRole role : clubRoles) {
            registeredClubs.add(role.getClub().getId());
        }

        if (registeredClubs.contains(clubId)) {
            Event registeredEvent = eventService.findByEventId(eventId);
            Student registeringStudent = studentService.findById(studentId);
            Set<Student> alreadyRegisteredStudents = studentService.findAllStudentRegisteredEvent(registeredEvent);

            if (alreadyRegisteredStudents.contains(registeringStudent)) {
                return ResponseEntity.ok(new MessageResponse("You are already registered."));
            }

            //other case we need to register student
            alreadyRegisteredStudents.add(registeringStudent);
            registeredEvent.setParticipants(alreadyRegisteredStudents);
            registeredEvent.setRemainingQuota(registeredEvent.getRemainingQuota() - 1);
            eventService.saveEvent(registeredEvent);
            return ResponseEntity.ok(new MessageResponse("You are registered."));
        } else {
            return ResponseEntity.ok(new MessageResponse("You should be member of the club."));
        }
    }

    @GetMapping(value = "/allEvents", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<List<Event>> allEvents(){
        return ResponseEntity.ok(eventService.findAll());
    }


}

