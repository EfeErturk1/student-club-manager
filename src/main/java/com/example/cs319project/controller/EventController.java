package com.example.cs319project.controller;


import com.example.cs319project.dto.EventDto;
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
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
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
        Event event = Event.builder().status("NOT_DECIDED").name(addEventRequest.getName()).description(addEventRequest.getDescription()).clubId(addEventRequest.getClubId()).quota(addEventRequest.getQuota()).remainingQuota(addEventRequest.getQuota()).eventDate(addEventRequest.getEventDate()).eventFinish(addEventRequest.getFinishDate()).photos(addEventRequest.getPhotos()).ge250(addEventRequest.getGe250()).build();
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

        if (eventService.findByEventId(joinEventRequest.getEventId()).getRemainingQuota() == 0) {
            return ResponseEntity.ok(new MessageResponse("Event is full"));
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

            //other case we need to register student after checking have an event at that time
            List<Event> registeredEvents = eventService.findAllEventParticipatedBy(studentService.findById(joinEventRequest.getStudentId()));
            for(Event event : registeredEvents){

                if(!((event.getEventFinish().compareTo((eventService.findByEventId(joinEventRequest.getEventId())).getEventDate()) < 0) || (event.getEventDate().compareTo(eventService.findByEventId(joinEventRequest.getEventId()).getEventFinish()) > 0))){
                    return ResponseEntity.ok(new MessageResponse("You have an event at that time."));
                }

            }

            alreadyRegisteredStudents.add(registeringStudent);
            registeredEvent.setParticipants(alreadyRegisteredStudents);
            registeredEvent.setRemainingQuota(registeredEvent.getRemainingQuota() - 1);
            studentService.findById(studentId).setGe250(studentService.findById(studentId).getGe250() + eventService.findByEventId(eventId).getGe250());
            studentService.saveorUpdateStudent(studentService.findById(studentId));
            eventService.saveEvent(registeredEvent);
            return ResponseEntity.ok(new MessageResponse("You are registered."));
        } else {
            return ResponseEntity.ok(new MessageResponse("You should be member of the club."));
        }
    }

    @PostMapping(value = "/leaveEvent")
    public ResponseEntity<?> leaveEvent(@Valid @RequestBody JoinEventRequest joinEventRequest) {
        if (studentService.findById(joinEventRequest.getStudentId()) == null) {
            return ResponseEntity.ok(new MessageResponse("Student doesnot exists"));
        }

        if (eventService.findByEventId(joinEventRequest.getEventId()) == null) {
            return ResponseEntity.ok(new MessageResponse("Event doesnot exists"));
        }


        int studentId = joinEventRequest.getStudentId();
        int eventId = joinEventRequest.getEventId();
        int clubId = eventService.findByEventId(eventId).getClubId();

        Event registeredEvent = eventService.findByEventId(eventId);
        Student registeringStudent = studentService.findById(studentId);
        Set<Student> alreadyRegisteredStudents = studentService.findAllStudentRegisteredEvent(registeredEvent);

        if (alreadyRegisteredStudents.contains(registeringStudent)) {
            alreadyRegisteredStudents.remove(registeringStudent);
            registeredEvent.setParticipants(alreadyRegisteredStudents);
            registeredEvent.setRemainingQuota(registeredEvent.getRemainingQuota() + 1);
            studentService.findById(studentId).setGe250(studentService.findById(studentId).getGe250() - eventService.findByEventId(eventId).getGe250());
            studentService.saveorUpdateStudent(studentService.findById(studentId));
            eventService.saveEvent(registeredEvent);
            return ResponseEntity.ok(new MessageResponse("You left the event registered."));
        }

        return ResponseEntity.ok(new MessageResponse("You are not registered to the event."));
    }

    @GetMapping(value = "/allEvents", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<List<Event>> allEvents(){
        List<Event> allEvents = eventService.findAll();
        List<EventResponse> events = new ArrayList<>();
        for(Event event: allEvents){
            EventResponse response = EventResponse.builder().eventId(event.getEventId()).status(event.getStatus()).clubId(event.getClubId()).build();
            events.add(response);
        }
        return ResponseEntity.ok(eventService.findAll());
    }

    @GetMapping(value = "/myEvents", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<List<Event>> getStudentEvents(@RequestParam(name = "id") int studentId){
        return ResponseEntity.ok(eventService.findAllEventParticipatedBy(studentService.findById(studentId)));
    }

    @GetMapping(value = "/clubEvents", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<List<Event>> getClubEvents(@RequestParam(name = "id") int clubId){
        List<Event> allEvents = eventService.findAll();
        List<Event> clubEvents = new ArrayList<>();
        for(Event event: allEvents){
            if(event.getClubId() == clubId) {
                clubEvents.add(event);
            }
        }
        return ResponseEntity.ok(clubEvents);
    }

    @GetMapping(value = "/eventView", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<Event> getSpecificEvent(@RequestParam(name = "id") int eventId) {
        Event event = eventService.findByEventId(eventId);
        if(event == null){
            return ResponseEntity.ok(null);
        }
        return ResponseEntity.ok(event);
    }


    @PostMapping(value = "/editEvent", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> editEvent(@Valid @RequestBody EventDto dto) {
        System.out.println(dto.getId());
        eventService.updateEvent(dto);
        return ResponseEntity.ok(new MessageResponse("Event has been updated"));
    }

}

