package com.example.cs319project.controller;


import com.example.cs319project.file.FileDB;
import com.example.cs319project.file.FileStorageService;
import com.example.cs319project.model.*;
import com.example.cs319project.model.clubstrategy.ClubRole;
import com.example.cs319project.model.clubstrategy.ClubRoleName;
import com.example.cs319project.model.request.*;
import com.example.cs319project.service.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
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
    //@Autowired
    //private FileStorageService storageService;


    @PostMapping(value = "/addDocument")
    public ResponseEntity<?> addDocument(@RequestParam("file") MultipartFile file, @RequestParam(name="json") String json) throws IOException {
        //FileDB fileDB = storageService.store(file);
        ObjectMapper mapper = new ObjectMapper();
        DocumentRequest request = mapper.readValue(json, DocumentRequest.class);
        Student student = studentService.findById(request.getAuthor());
        Document document = Document.builder().author(student).document_name(request.getDocument_name()).submission_date(request.getSubmission_date()).build();
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

