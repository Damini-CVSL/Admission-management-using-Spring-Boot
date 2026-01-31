package com.example.AdmissionManagement.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.AdmissionManagement.entities.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {
	Optional<Student> findByAdmission_AdmissionNumber(String admissionNumer);
    
    List<Student> findByClassSection_Id(Long classSectionId);
}
