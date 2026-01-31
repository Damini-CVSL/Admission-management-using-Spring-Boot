package com.example.AdmissionManagement.services;


import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.AdmissionManagement.entities.Admission;
import com.example.AdmissionManagement.entities.AdmissionAudit;
import com.example.AdmissionManagement.repositories.AdmissionAuditRepository;
import com.example.AdmissionManagement.services.AdmissionAuditService;

@Service
@Transactional
public class AdmissionAuditServiceImpl implements AdmissionAuditService {

    private final AdmissionAuditRepository admissionAuditRepository;

    public AdmissionAuditServiceImpl(
            AdmissionAuditRepository admissionAuditRepository) {
        this.admissionAuditRepository = admissionAuditRepository;
    }

    @Override
    public void recordAction(
            Admission admission,
            String action,
            String actionBy) {

        AdmissionAudit audit = new AdmissionAudit();
        audit.setAdmission(admission);
        audit.setAction(action);
        audit.setActionBy(actionBy);
        audit.setActionTime(LocalDateTime.now());

        admissionAuditRepository.save(audit);
    }
}

