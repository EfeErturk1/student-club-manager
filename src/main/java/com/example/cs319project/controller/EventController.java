package com.example.cs319project.controller;


import com.example.cs319project.dto.EventDto;
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
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/event")
public class EventController {

    private final ClubService clubService;
    private final ClubRoleService clubRoleService;
    private final StudentService studentService;
    private final EventService eventService;
    private final NotificationService notificationService;

    // whenever a new event comes users should be notified
    @PostMapping(value = "/addEvent")
    public ResponseEntity<Event> addEvent(@Valid @RequestBody EventDto addEventRequest) {
        Event event = Event.builder().status("NOT_DECIDED").name(addEventRequest.getName()).description(addEventRequest.getDescription()).clubId(addEventRequest.getClubId()).quota(addEventRequest.getQuota()).remainingQuota(addEventRequest.getQuota()).eventDate(addEventRequest.getEventDate()).eventFinish(addEventRequest.getFinishDate()).photos(addEventRequest.getPhotos()).ge250(addEventRequest.getGe250()).build();
        eventService.addEvent(event);
        return ResponseEntity.ok(event);
    }

    //whenever an event is deleted, users should not continue to be the participants of an unexisting event
    @PostMapping(value = "/deleteEvent")
    public ResponseEntity<?> deleteEvent(@Valid @RequestBody IdHolder deleteEventRequest) {
        eventService.deleteEvent(eventService.findByEventId(deleteEventRequest.getId()));
        return ResponseEntity.ok(new MessageResponse("Event deleted successfully!"));
    }


    // to join an event user should not have any other event at the same time
    // a participant can join only one event at a particular time
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


    //whenever student leaves an event, it should be removed from participants
    // a participant must have been registered to event to leave
    @PostMapping(value = "/leaveEvent")
    public ResponseEntity<?> leaveEvent(@Valid @RequestBody JoinEventRequest leaveEventRequest) {
        if (studentService.findById(leaveEventRequest.getStudentId()) == null) {
            return ResponseEntity.ok(new MessageResponse("Student doesnot exists"));
        }

        if (eventService.findByEventId(leaveEventRequest.getEventId()) == null) {
            return ResponseEntity.ok(new MessageResponse("Event doesnot exists"));
        }

        int studentId = leaveEventRequest.getStudentId();
        int eventId = leaveEventRequest.getEventId();
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
    public @ResponseBody ResponseEntity<?> allEvents(){
        List<Event> allEvents = eventService.findAll();
        List<EventResponse> events = new ArrayList<>();
        for(Event event: allEvents){
            EventResponse response = EventResponse.builder().eventId(event.getEventId()).status(event.getStatus()).clubName(clubService.findById(event.getClubId()).getName())
                    .photos(event.getPhotos()).name(event.getName()).clubId(event.getClubId()).description(event.getDescription())
                    .eventDate(event.getEventDate()).eventFinish(event.getEventFinish()).quota(event.getQuota()).remainingQuota(event.getRemainingQuota())
                    .ge250(event.getGe250()).build();
            events.add(response);
        }
        return ResponseEntity.ok(events);
    }

    @GetMapping(value = "/myEvents", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<?> getStudentEvents(@RequestParam(name = "id") int studentId){
        List<Event> myEvents = eventService.findAllEventParticipatedBy(studentService.findById(studentId));
        List<EventResponse> events = new ArrayList<>();
        for(Event event: myEvents){
            EventResponse response = EventResponse.builder().eventId(event.getEventId()).status(event.getStatus()).clubName(clubService.findById(event.getClubId()).getName())
                    .photos(event.getPhotos()).name(event.getName()).clubId(event.getClubId()).description(event.getDescription())
                    .eventDate(event.getEventDate()).eventFinish(event.getEventFinish()).quota(event.getQuota()).remainingQuota(event.getRemainingQuota())
                    .ge250(event.getGe250()).build();
            events.add(response);
        }
        return ResponseEntity.ok(events);
    }

    @GetMapping(value = "/clubEvents", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<?> getClubEvents(@RequestParam(name = "id") int clubId){
        List<Event> allEvents = eventService.findAll();
        List<Event> clubEvents = new ArrayList<>();
        for(Event event: allEvents){
            if(event.getClubId() == clubId) {
                clubEvents.add(event);
            }
        }
        List<EventResponse> events = new ArrayList<>();
        for(Event event: clubEvents){
            EventResponse response = EventResponse.builder().eventId(event.getEventId()).status(event.getStatus()).clubName(clubService.findById(event.getClubId()).getName())
                    .photos(event.getPhotos()).name(event.getName()).clubId(event.getClubId()).description(event.getDescription())
                    .eventDate(event.getEventDate()).eventFinish(event.getEventFinish()).quota(event.getQuota()).remainingQuota(event.getRemainingQuota())
                    .ge250(event.getGe250()).build();
            events.add(response);
        }
        return ResponseEntity.ok(events);
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
        Event event = eventService.findByEventId(dto.getId());
        String pht = event.getPhotos();
        int clb_id = event.getClubId();
        int r_quota = dto.getQuota()  - event.getQuota() + event.getRemainingQuota();
        eventService.updateEvent(dto);
        event.setStatus("NOT_DECIDED");
        event.setClubId(clb_id);
        event.setPhotos(pht);
        event.setRemainingQuota(r_quota);
        eventService.saveorUpdate(event);
        return ResponseEntity.ok(new MessageResponse("Event has been updated"));
    }

}

