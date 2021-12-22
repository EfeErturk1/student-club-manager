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
@RequestMapping("/advisor")
public class AdvisorController {

    private final ClubService clubService;
    private final ClubRoleService clubRoleService;
    private final StudentService studentService;
    private final EventService eventService;
    private final AdvisorService advisorService;

    @GetMapping(value= "/advisorView", produces =  MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<Advisor> advisorView(@Valid @RequestBody IdHolder idHolder) {
        Advisor advisor = advisorService.findById(idHolder.getId());
        return ResponseEntity.ok(advisor);
    }

    @GetMapping(value= "/advisorOfClubView", produces =  MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<Advisor> advisorOfClubView(@Valid @RequestBody Club club) {
        Advisor advisor = advisorService.findByClub(club);
        return ResponseEntity.ok(advisor);
    }

    @GetMapping(value = "/allAdvisors", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<List<Advisor>> allAdvisors(){
        return ResponseEntity.ok(advisorService.findAll());
    }

    @PostMapping(value = "/rejectEvent", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> rejectEvent(@Valid @RequestBody IdHolder idHolder) {
        Event event = eventService.findByEventId(idHolder.getId());
        if(event == null) {
            return ResponseEntity.ok(new MessageResponse("There is no such an event"));
        }
        event.setStatus("REJECTED");
        eventService.saveEvent(event);
        return ResponseEntity.ok(new MessageResponse("Event has been rejected successfully"));
    }

    @PostMapping(value = "/acceptEvent", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> acceptEvent(@Valid @RequestBody IdHolder idHolder) {
        Event event = eventService.findByEventId(idHolder.getId());
        if(event == null) {
            return ResponseEntity.ok(new MessageResponse("There is no such an event"));
        }
        event.setStatus("ACCEPTED");
        eventService.saveEvent(event);
        return ResponseEntity.ok(new MessageResponse("Event has been accepted successfully"));
    }
}
