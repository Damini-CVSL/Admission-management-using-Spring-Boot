package com.example.AdmissionManagement.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.AdmissionManagement.entities.AdmissionAudit;

public interface AdmissionAuditRepository extends JpaRepository<AdmissionAudit, Long> {
    List<AdmissionAudit> findByAdmission_Id(Long admissionId);
}


