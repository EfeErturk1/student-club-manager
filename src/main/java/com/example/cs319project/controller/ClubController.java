package com.example.cs319project.controller;

import com.example.cs319project.model.request.SignupRequest;
import com.example.cs319project.service.ClubService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/club")
public class ClubController {
    private final ClubService clubService;

    /*@PostMapping('/addClub')
    public ResponseEntity<?> addClub(@Valid @RequestBody ClubRequest signUpRequest) {

    }*/
