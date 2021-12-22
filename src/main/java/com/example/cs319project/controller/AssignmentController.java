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
import java.util.List;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/assignment")
public class AssignmentController {

    private final ClubService clubService;
    private final ClubRoleService clubRoleService;
    private final StudentService studentService;
    private final DocumentService documentService;
    private final AssignmentService assignmentService;


    @PostMapping(value = "/addAssignment")
    public ResponseEntity<?> addAssignment(@Valid @RequestBody Assignment request) {
        Assignment assignment = Assignment.builder().due_date(request.getDue_date()).name(request.getName()).description(request.getDescription()).clubId(request.getClubId()).build();
        assignmentService.createNewAssignment(assignment);
        return ResponseEntity.ok(new MessageResponse("Assignment added successfully!"));
    }

    @PostMapping(value = "/deleteAssignment")
    public ResponseEntity<?> deleteAssignment(@Valid @RequestBody IdHolder id) {
        assignmentService.deleteAssignment(assignmentService.findByAssignmentId(id.getId()));
        return ResponseEntity.ok(new MessageResponse("Assignment deleted successfully!"));
    }


    @GetMapping(value = "/allAssignments", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<List<Assignment>> all(){
        return ResponseEntity.ok(assignmentService.findAll());
    }

    @GetMapping(value= "/assignmentView", produces =  MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<Assignment> getSpecificAssignment(@Valid @RequestBody IdHolder idHolder) {
        Assignment assignment = assignmentService.findByAssignmentId(idHolder.getId());
        return ResponseEntity.ok(assignment);
    }
}

