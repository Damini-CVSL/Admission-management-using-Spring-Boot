package com.example.AdmissionManagement.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.AdmissionManagement.dtos.StudentResponseDto;
import com.example.AdmissionManagement.entities.Admission;


public interface StudentService {
	StudentResponseDto createStudentFromAdmission(Admission admission);
	
	List<StudentResponseDto> getStudentsByClassSection(Long classSectionId);
}
