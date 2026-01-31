package com.example.AdmissionManagement.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.AdmissionManagement.entities.ClassSection;
import com.example.AdmissionManagement.services.ClassSectionService;

@RestController
@RequestMapping("/api/class-sections")
public class ClassSectionController {

    private final ClassSectionService classSectionService;

    public ClassSectionController(ClassSectionService classSectionService) {
        this.classSectionService = classSectionService;
    }

    //getting section for a class 
    
    @GetMapping("/available")
    public ResponseEntity<ClassSection> getAvailableSection(
            @RequestParam String className) {

        return ResponseEntity.ok(
                classSectionService.getAvailableSection(className));
    }
}

