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
@RequestMapping("/document")
public class DocumentController {

    private final ClubService clubService;
    private final ClubRoleService clubRoleService;
    private final StudentService studentService;
    private final DocumentService documentService;


    @PostMapping(value = "/addDocument")
    public ResponseEntity<?> addEvent(@Valid @RequestBody AddEventRequest addEventRequest) {
        //Document document = Document.builder().name(addEventRequest.getName()).description(addEventRequest.getDescription()).clubId(addEventRequest.getClubId()).quota(addEventRequest.getQuota()).remainingQuota(addEventRequest.getQuota()).eventDate(addEventRequest.getEventDate()).build();
        //documentService.addDocument(document);
        return ResponseEntity.ok(new MessageResponse("Event added successfully!"));
    }

    @PostMapping(value = "/deleteDocument")
    public ResponseEntity<?> deleteDocument(@Valid @RequestBody IdHolder deleteEventRequest) {
        //documentService.removeDocument(documentService.findByDocumentId(deleteEventRequest.getId()));
        return ResponseEntity.ok(new MessageResponse("Document deleted successfully!"));
    }


    @GetMapping(value = "/allDocuments", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<List<Document>> allEvents(){
        return ResponseEntity.ok(documentService.findAll());
    }


}

