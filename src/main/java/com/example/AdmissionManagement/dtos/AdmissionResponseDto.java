package com.example.AdmissionManagement.dtos;

import com.example.AdmissionManagement.entities.AdmissionStatus;

public record AdmissionResponseDto(
        Long id,
        String studentName,
        int age,
        String gender,
        String appliedClass,
        String className,          // ✅ ADD THIS
        String section,
        String admissionNumber,
        AdmissionStatus status,
        boolean isOffline,
        String userName
) {}
