package com.example.cs319project.controller;

import com.example.cs319project.controller.util.FileUploadUtil;
import com.example.cs319project.model.Club;
import com.example.cs319project.model.request.ClubRequest;
import com.example.cs319project.model.request.MessageResponse;
import com.example.cs319project.model.request.SignupRequest;
import com.example.cs319project.service.ClubService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/club")
public class ClubController {
    private final ClubService clubService;

    @PostMapping(value = "/addClub")
    public ResponseEntity<?> addClub(@Valid @RequestBody ClubRequest clubRequest){
        Club club = new Club();
        club.setName(clubRequest.getName());
        club.setDescription(clubRequest.getDescription());
        clubService.createNewClub(club);
        return ResponseEntity.ok(new MessageResponse("Club added successfully!"));
    }


}
