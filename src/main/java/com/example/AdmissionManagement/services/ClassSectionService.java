package com.example.AdmissionManagement.services;

import org.springframework.stereotype.Service;

import com.example.AdmissionManagement.entities.ClassSection;


public interface ClassSectionService {
	ClassSection getAvailableSection(String className);
	
	void IncrementStrength(ClassSection classSection);
}
