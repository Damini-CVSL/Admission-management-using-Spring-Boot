package com.example.AdmissionManagement.mappers;

import com.example.AdmissionManagement.dtos.StudentResponseDto;
import com.example.AdmissionManagement.entities.Student;

public class StudentMapper {

  
    public static StudentResponseDto toResponse(Student student) {
        if (student == null) return null;

        return new StudentResponseDto(
                student.getId(),
                student.getStudentName(),                           // name
                student.getAge() != null ? student.getAge() : 0,    // age
                student.getGender() != null ? student.getGender() : "N/A", // gender
                student.getAdmissionNumber(),                       // admission number
                student.getClassSection() != null 
                        ? student.getClassSection().getClassName() 
                        : "N/A",                                   // className
                student.getSection() != null ? student.getSection() : "N/A" // section
        );
    }
}

