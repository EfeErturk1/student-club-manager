package com.example.cs319project.controller;

import java.util.List;
import java.util.stream.Collectors;

import com.example.cs319project.file2.FileInfo;
import com.example.cs319project.file2.FilesStorageService;
import com.example.cs319project.model.Assignment;
import com.example.cs319project.model.Club;
import com.example.cs319project.model.Event;
import com.example.cs319project.model.Student;
import com.example.cs319project.model.request.MessageResponse;
import com.example.cs319project.service.AssignmentService;
import com.example.cs319project.service.ClubService;
import com.example.cs319project.service.EventService;
import com.example.cs319project.service.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

// This is the controller where file upload process is managed

@Slf4j
@RequiredArgsConstructor
@RestController
public class FilesController {

    @Autowired
    FilesStorageService storageService;

    private final StudentService studentService;
    private final AssignmentService assignmentService;
    private final ClubService clubService;
    private final EventService eventService;


    @PostMapping("/upload")
    public ResponseEntity<MessageResponse> uploadFile(@RequestParam("file") MultipartFile file) {
        String message = "";
        try {
            storageService.save(file);

            message = "Uploaded the file successfully: " + file.getOriginalFilename();

            return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse(message));
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new MessageResponse(message));
        }
    }

    @PostMapping("/uploadProfilePic")
    public ResponseEntity<MessageResponse> uploadProfilePic(@RequestParam("file") MultipartFile file, @RequestParam(name="id") int id) {
        String message = "";
        try {
            storageService.save(file);
            Student student = studentService.findById(id);
            student.setProfilePhotoName(file.getOriginalFilename());
            studentService.saveorUpdateStudent(student);
            message = "Uploaded profile pic the file successfully: " + file.getOriginalFilename();

            return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse(message));
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new MessageResponse(message));
        }
    }

    @PostMapping("/uploadClubPic")
    public ResponseEntity<MessageResponse> uploadClubPic(@RequestParam("file") MultipartFile file, @RequestParam(name="id") int id){
        String message = "";
        try {
            storageService.save(file);
            Club club = clubService.findById(id);
            club.setPhotos(file.getOriginalFilename());
            clubService.saveClub(club);
            message = "Uploaded club pic the file successfully: " + file.getOriginalFilename();

            return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse(message));
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new MessageResponse(message));
        }
    }

    @PostMapping("/uploadEventPic")
    public ResponseEntity<MessageResponse> uploadEventPic(@RequestParam("file") MultipartFile file, @RequestParam(name="id") int id){
        String message = "";
        try {
            storageService.save(file);
            Event event = eventService.findByEventId(id);
            event.setPhotos(file.getOriginalFilename());
            eventService.saveEvent(event);
            message = "Uploaded event pic the file successfully: " + file.getOriginalFilename();

            return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse(message));
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new MessageResponse(message));
        }
    }

    @PostMapping("/uploadAssignmentDocument")
    public ResponseEntity<MessageResponse> uploadAssignmentDocument(@RequestParam("file") MultipartFile file, @RequestParam(name="id") int id) {
        String message = "";
        try {
            storageService.save(file);
            Assignment assignment = assignmentService.findByAssignmentId(id);
            assignment.setAssignmentFile(file.getOriginalFilename());
            assignmentService.saveAssignment(assignment);
            message = "Uploaded assignment successfully: " + file.getOriginalFilename();

            return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse(message));
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new MessageResponse(message));
        }
    }

    @GetMapping("/files")
    public ResponseEntity<List<FileInfo>> getListFiles() {
        List<FileInfo> fileInfos = storageService.loadAll().map(path -> {
            String filename = path.getFileName().toString();
            String url = MvcUriComponentsBuilder
                    .fromMethodName(FilesController.class, "getFile", path.getFileName().toString()).build().toString();

            return new FileInfo(filename, url);
        }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(fileInfos);
    }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        Resource file = storageService.load(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @GetMapping("/getFilePathStudent")
    @ResponseBody
    public ResponseEntity<String> getFile(@RequestParam(name="id") int idHolder) {
        Student student = studentService.findById(idHolder);
       return ResponseEntity.ok(student.getProfilePhotoName());
    }
}
