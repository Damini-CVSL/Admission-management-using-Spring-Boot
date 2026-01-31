package com.example.AdmissionManagement.dtos;

import java.time.LocalDateTime;

public record AdmissionAuditResponseDto(
		Long id,
		Long admissionId,
		String action,
		String actionBy,
		LocalDateTime actionTime
		) {

}
