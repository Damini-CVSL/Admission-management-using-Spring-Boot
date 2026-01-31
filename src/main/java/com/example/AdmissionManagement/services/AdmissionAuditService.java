package com.example.AdmissionManagement.services;

import org.springframework.stereotype.Service;

import com.example.AdmissionManagement.entities.Admission;


public interface AdmissionAuditService {
	void recordAction(
			Admission admission,
			String action,
			String actionBy
			);
}
