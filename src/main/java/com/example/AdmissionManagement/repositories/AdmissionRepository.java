package com.example.AdmissionManagement.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.AdmissionManagement.entities.Admission;
import com.example.AdmissionManagement.entities.AdmissionAudit;
import com.example.AdmissionManagement.entities.AdmissionStatus;

public interface AdmissionRepository extends JpaRepository<Admission, Long>{
	List<Admission> findByUser_Id(Long userId);

	List<Admission> findByStatus(AdmissionStatus status);
	

	List<Admission> findByAppliedClass(String appliedClass);
	
	long countByStatus(AdmissionStatus status);
	
	List<Admission> findByStatusAndUserId(AdmissionStatus status, Long userId);
}
