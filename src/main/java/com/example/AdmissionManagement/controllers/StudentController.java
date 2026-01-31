package com.example.AdmissionManagement.controllers;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.AdmissionManagement.dtos.StudentResponseDto;
import com.example.AdmissionManagement.services.StudentService;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/class-section/{classSectionId}")
    public ResponseEntity<List<StudentResponseDto>> getByClassSection(@PathVariable Long classSectionId) {
        return ResponseEntity.ok(studentService.getStudentsByClassSection(classSectionId));
    }
}
