package com.example.AdmissionManagement.services;


import com.example.AdmissionManagement.dtos.AdmissionRequestDto;
import com.example.AdmissionManagement.dtos.AdmissionResponseDto;
import com.example.AdmissionManagement.entities.Admission;
import com.example.AdmissionManagement.entities.AdmissionStatus;

import java.util.List;

public interface AdmissionService {

    AdmissionResponseDto applyOnline(AdmissionRequestDto requestDto, Long userId);

    AdmissionResponseDto applyOffline(AdmissionRequestDto requestDto);

    AdmissionResponseDto approveAdmission(Long admissionId, String approvedBy);

    AdmissionResponseDto rejectAdmission(Long admissionId, String rejectedBy);

    List<AdmissionResponseDto> getAdmissionByStatus(AdmissionStatus status);
    Admission getLatestAdmissionForUser(Long userId);

        default List<AdmissionResponseDto> getPendingAdmissions() {
        return getAdmissionByStatus(AdmissionStatus.PENDING);
    }

    default List<AdmissionResponseDto> getApprovedAdmissions() {
        return getAdmissionByStatus(AdmissionStatus.APPROVED);

    }
    List<AdmissionResponseDto> getApprovedAdmissionsForUser(Long userId, String role);

}

