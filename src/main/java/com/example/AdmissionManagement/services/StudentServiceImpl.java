package com.example.AdmissionManagement.services;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.AdmissionManagement.dtos.StudentResponseDto;
import com.example.AdmissionManagement.entities.Admission;
import com.example.AdmissionManagement.entities.ClassSection;
import com.example.AdmissionManagement.entities.Student;
import com.example.AdmissionManagement.mappers.StudentMapper;
import com.example.AdmissionManagement.repositories.StudentRepository;

@Service
@Transactional
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final ClassSectionService classSectionService;

    public StudentServiceImpl(StudentRepository studentRepository,
                              ClassSectionService classSectionService) {
        this.studentRepository = studentRepository;
        this.classSectionService = classSectionService;
    }

    @Override
    public StudentResponseDto createStudentFromAdmission(Admission admission) {

        ClassSection section =
                classSectionService.getAvailableSection(admission.getAppliedClass());

        Student student = new Student();
        student.setStudentName(admission.getStudentName());
        student.setAdmissionNumber(admission.getAdmissionNumber());
        student.setAge(admission.getAge());
        student.setGender(admission.getGender());
        student.setSection(section.getSection());
        student.setJoining(LocalDate.now());
        student.setAdmission(admission);
        student.setClassSection(section);

        Student savedStudent = studentRepository.save(student);
        classSectionService.IncrementStrength(section);

        return StudentMapper.toResponse(savedStudent);
    }

    @Override
    public List<StudentResponseDto> getStudentsByClassSection(Long classSectionId) {
        return studentRepository.findByClassSection_Id(classSectionId)
                .stream()
                .map(StudentMapper::toResponse)
                .collect(Collectors.toList());
    }
}
