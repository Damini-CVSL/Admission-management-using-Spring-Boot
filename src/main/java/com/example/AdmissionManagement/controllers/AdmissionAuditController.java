package com.example.AdmissionManagement.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.example.AdmissionManagement.entities.AdmissionAudit;
import com.example.AdmissionManagement.repositories.AdmissionAuditRepository;

@RestController
@RequestMapping("/api/admission-audits")
public class AdmissionAuditController {

    private final AdmissionAuditRepository auditRepository;

    public AdmissionAuditController(AdmissionAuditRepository auditRepository) {
        this.auditRepository = auditRepository;
    }

    @GetMapping("/{admissionId}")
    public List<AdmissionAudit> getAuditByAdmissionId(
            @PathVariable Long admissionId) {
        return auditRepository.findByAdmission_Id(admissionId);
    }
}

