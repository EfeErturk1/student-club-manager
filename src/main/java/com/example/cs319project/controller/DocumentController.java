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
    public ResponseEntity<?> addDocument(@Valid @RequestBody Document request) {
        Document document = Document.builder().author(request.getAuthor()).document_name(request.getDocument_name()).document_file(request.getDocument_file()).submission_date(request.getSubmission_date()).build();
        documentService.addDocument(document);
        return ResponseEntity.ok(new MessageResponse("Document added successfully!"));
    }

    @PostMapping(value = "/deleteDocument")
    public ResponseEntity<?> deleteDocument(@Valid @RequestBody IdHolder id) {
        documentService.removeDocument(documentService.findByDocumentId(id.getId()));
        return ResponseEntity.ok(new MessageResponse("Document deleted successfully!"));
    }


    @GetMapping(value = "/allDocuments", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<List<Document>> all(){
        return ResponseEntity.ok(documentService.findAll());
    }

    @GetMapping(value= "/documentView", produces =  MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<Document> getSpecificDocument(@RequestParam(name = "id") int  idHolder) {
        Document doc = documentService.findByDocumentId(idHolder);
        return ResponseEntity.ok(doc);
    }
}

