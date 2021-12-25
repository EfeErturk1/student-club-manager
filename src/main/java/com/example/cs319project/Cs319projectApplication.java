package com.example.cs319project;

import com.example.cs319project.file2.FilesStorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@SpringBootApplication
@RestController
public class Cs319projectApplication implements CommandLineRunner {
    @Resource
    FilesStorageService storageService;

    public static void main(String[] args) {
        SpringApplication.run(Cs319projectApplication.class, args);
    }
    @GetMapping("/hello")
    public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
        return String.format("Hello %s!", name);
    }

    @Override
    public void run(String... arg) throws Exception {
        //storageService.deleteAll();
        //storageService.init();
    }
}
