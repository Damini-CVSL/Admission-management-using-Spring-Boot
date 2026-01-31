package com.example.AdmissionManagement.dtos;

import java.time.LocalDateTime;

public record ClassSectionResponseDto(
		Long id,
		String className,
		String sectionName,
		Integer maxStrength,
		Integer currectStrength
		) {

}
