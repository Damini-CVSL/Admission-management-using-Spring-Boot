package com.example.AdmissionManagement.dtos;

public record StudentResponseDto(
		Long id,
		String name,
		int age,
		String gender,
		
		String admissionNumber,
		String className,
		String section
		) {
	
}
