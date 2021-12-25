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
@RequestMapping("/assignment")
public class AssignmentController {

    private final ClubService clubService;
    private final ClubRoleService clubRoleService;
    private final StudentService studentService;
    private final DocumentService documentService;
    private final AssignmentService assignmentService;
    private final NotificationService notificationService;


    @PostMapping(value = "/addAssignment")
    public ResponseEntity<?> addAssignment(@Valid @RequestBody AssignmentCreateRequest request) {
        Set<Student> assignedStudent = new HashSet<>();

        for(int studentId: request.getAssignees()){
            Student student = studentService.findById(studentId);
            assignedStudent.add(student);
        }
        Assignment assignment = Assignment.builder().due_date(request.getDue_date()).name(request.getName()).description(request.getDescription()).clubId(request.getClubId()).assignees(assignedStudent).build();
        assignmentService.createNewAssignment(assignment);

        List<ClubRole> clubRoles = clubRoleService.findByClubId(request.getClubId());
        Set<Student> notifieds = new HashSet<>();

        for(ClubRole role: clubRoles){
            Student student = studentService.findById(role.getStudent().getId());
            notifieds.add(student);
        }

        String str = "Assignment with name " + assignment.getName() + " is assigned!";

        Notification notification = Notification.builder()
                .date(null) // request.getDue_date().toString() niyeyse stringe çevirmiyo, o yüzden null
                .description(str)
                .clubId(request.getClubId())
                .isRequest(false)
                .notified_people(notifieds).build();
        notificationService.createNewNotification(notification);

        return ResponseEntity.ok(new MessageResponse("Assignment added successfully!"));
    }

    @PostMapping(value = "/addDocumentsToAssignment")
    public ResponseEntity<?> addDocumentToAssignment(@Valid @RequestBody  DocumentAssignmentIdHolder idHolder){
        Assignment assignment = assignmentService.findByAssignmentId(idHolder.getAssignmentId());
        Set<Document> documentOfAssignment = new HashSet<>();

        for(int id: idHolder.getDocumentIds()){
            Document document = documentService.findByDocumentId(id);
            document.setBelongsToAssignment(assignment);
            documentService.saveDocument(document);
            documentOfAssignment.add(document);
        }
        assignment.setDocuments(documentOfAssignment);
        assignmentService.saveAssignment(assignment);
        return ResponseEntity.ok(new MessageResponse("Document added to assignment successfully!"));
    }


    @PostMapping(value = "/deleteAssignment")
    public ResponseEntity<?> deleteAssignment(@Valid @RequestBody IdHolder id) {
        assignmentService.deleteAssignment(assignmentService.findByAssignmentId(id.getId()));
        return ResponseEntity.ok(new MessageResponse("Assignment deleted successfully!"));
    }

    @PostMapping(value="/updateSubmission")
    public ResponseEntity<?> updateSubmissionInfo(@Valid @RequestBody SubmissionRequest submission) {
        Assignment assignment = assignmentService.findByAssignmentId(submission.getId());
        assignment.setSubmissionDes(submission.getDescription());
        assignmentService.saveAssignment(assignment);
        return ResponseEntity.ok(new MessageResponse("Assignment has been submitted successfully!"));
    }


    @GetMapping(value = "/allAssignments", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<List<Assignment>> all(){
        return ResponseEntity.ok(assignmentService.findAll());
    }

    @GetMapping(value= "/assignmentView", produces =  MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<Assignment> getSpecificAssignment(@RequestParam(name = "id") int  idHolder) {
        Assignment assignment = assignmentService.findByAssignmentId(idHolder);
        return ResponseEntity.ok(assignment);
    }

    @PostMapping(value = "/completeAssignment")
    public ResponseEntity<?> completeAssignment(@Valid @RequestBody IdHolder id) {
        Assignment assignment = assignmentService.findByAssignmentId(id.getId());
        if (assignment == null)
            return ResponseEntity.ok(new MessageResponse("There is no such assignment"));
        assignment.setStatus(true);
        assignmentService.createNewAssignment(assignment);

        Set<Club> notifieds = new HashSet<>();
        notifieds.add(clubService.findById(assignment.getClubId()));

        String str = "Assignment with name " + assignment.getName() + " is completed!";

        Notification notification = Notification.builder()
                .date(null)
                .description(str)
                .clubId(assignment.getClubId())
                .isRequest(false)
                .notified_clubs(notifieds).build();
        notificationService.createNewNotification(notification);

        return ResponseEntity.ok(new MessageResponse("Assignment completed successfully!"));
    }

    @GetMapping(value = "/getAssignment")
    public @ResponseBody ResponseEntity<Assignment> getAssignment(@Valid @RequestBody IdHolder id) {
        return ResponseEntity.ok(assignmentService.findByAssignmentId(id.getId()));
    }

    @GetMapping(value = "/getStudentAssignment")
    public @ResponseBody ResponseEntity<?> getStudentAssignment(@RequestParam(name = "id") int idHolder) {
        Set<Assignment> myAssignments = studentService.findById(idHolder).getAssignments();
        List<AssignmentResponse> assignments = new ArrayList<>();
        for(Assignment assignment: myAssignments){
            AssignmentResponse response = AssignmentResponse.builder().assignmentId(assignment.getAssignmentId()).due_date(assignment.getDue_date())
                    .name(assignment.getName()).description(assignment.getDescription()).clubId(assignment.getClubId())
                    .clubName(clubService.findById(assignment.getClubId()).getName()).status(assignment.isStatus())
                    .file(assignment.getAssignmentFile()).assignees(assignment.getAssignees()).documents(assignment.getDocuments()).build();
            assignments.add(response);
        }
        return ResponseEntity.ok(assignments);
    }

    @GetMapping(value = "/getClubAssignment")
    public @ResponseBody ResponseEntity<List<Assignment>> getClubAssignment(@RequestParam(name = "id") int idHolder) {
        List<Assignment> assignments = assignmentService.findAll();
        List<Assignment> assignmentOfClub = new ArrayList<>();
        for(Assignment assignment: assignments){
            if(assignment.getClubId() == idHolder){
                assignmentOfClub.add(assignment);
            }
        }
        return ResponseEntity.ok(assignmentOfClub);
    }
}

