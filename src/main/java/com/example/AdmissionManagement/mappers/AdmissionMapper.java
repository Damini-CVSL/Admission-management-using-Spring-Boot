package com.example.AdmissionManagement.mappers;

import com.example.AdmissionManagement.dtos.AdmissionResponseDto;
import com.example.AdmissionManagement.entities.Admission;

public class AdmissionMapper {

    public static AdmissionResponseDto toResponse(Admission admission) {
        if (admission == null) return null;

        String className = "N/A";
        String section = "N/A";

        if (admission.getClassSection() != null) {
            className = admission.getClassSection().getClassName();
            section = admission.getClassSection().getSection();
        }

        return new AdmissionResponseDto(
                admission.getId(),
                admission.getStudentName(),
                admission.getAge() != null ? admission.getAge() : 0,
                admission.getGender() != null ? admission.getGender() : "N/A",
                admission.getAppliedClass(),
                className,                                         section,
                admission.getAdmissionNumber(),
                admission.getStatus(),
                admission.getIsOffline() != null ? admission.getIsOffline() : false,
                admission.getUser() != null
                        ? admission.getUser().getUserName()
                        : "N/A"
        );
    }
}



