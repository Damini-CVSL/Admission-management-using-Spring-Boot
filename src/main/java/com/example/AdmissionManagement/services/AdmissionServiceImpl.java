package com.example.AdmissionManagement.services;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.AdmissionManagement.dtos.AdmissionRequestDto;
import com.example.AdmissionManagement.dtos.AdmissionResponseDto;
import com.example.AdmissionManagement.entities.Admission;
import com.example.AdmissionManagement.entities.AdmissionStatus;
import com.example.AdmissionManagement.entities.ClassSection;
import com.example.AdmissionManagement.entities.Student;
import com.example.AdmissionManagement.entities.User;
import com.example.AdmissionManagement.mappers.AdmissionMapper;
import com.example.AdmissionManagement.repositories.AdmissionRepository;
import com.example.AdmissionManagement.repositories.ClassSectionRepository;
import com.example.AdmissionManagement.repositories.StudentRepository;
import com.example.AdmissionManagement.repositories.UserRepository;
import com.example.AdmissionManagement.services.AdmissionAuditService;
import com.example.AdmissionManagement.services.AdmissionService;
import com.example.AdmissionManagement.services.StudentService;

@Service
@Transactional
public class AdmissionServiceImpl implements AdmissionService {

    private final AdmissionRepository admissionRepository;
    private final UserRepository userRepository;
    private final StudentService studentService ;
    private final AdmissionAuditService auditService;
    private final ClassSectionRepository classSectionRepository;
    private final StudentRepository studentRepository;

    public AdmissionServiceImpl(AdmissionRepository admissionRepository,
                                UserRepository userRepository,
                                StudentService studentService,
                                AdmissionAuditService auditService,
                                ClassSectionRepository classSectionRepository,
                                StudentRepository studentRepository) {
        this.admissionRepository = admissionRepository;
        this.userRepository = userRepository;
        this.studentService = studentService;
        this.auditService = auditService;
        this.classSectionRepository = classSectionRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public AdmissionResponseDto applyOnline(AdmissionRequestDto requestDto, Long userId) {

        List<Admission> existingAdmissions = admissionRepository.findByUser_Id(userId);

        if (!existingAdmissions.isEmpty()) {
            Admission existing = existingAdmissions.get(0);

                        if (existing.getStatus() == AdmissionStatus.PENDING) {
                throw new RuntimeException("Your admission is still pending approval");
            }
            if (existing.getStatus() == AdmissionStatus.APPROVED) {
                throw new RuntimeException("Your admission is already approved");
            }
            if (existing.getStatus() == AdmissionStatus.REJECTED) {
                throw new RuntimeException("Your admission was rejected. Contact admin");
            }
        }

                User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

                Admission admission = new Admission();
        admission.setStudentName(requestDto.getStudentName());
        admission.setAge(requestDto.getAge());
        admission.setGender(requestDto.getGender());
        admission.setAppliedClass(requestDto.getAppliedClass());
        admission.setStatus(AdmissionStatus.PENDING);
        admission.setIsOffline(false);
        admission.setUser(user);

                admission = admissionRepository.save(admission);

                return AdmissionMapper.toResponse(admission);
    }



    @Override
    public AdmissionResponseDto applyOffline(AdmissionRequestDto requestDto) {
        Admission admission = new Admission();
        admission.setStudentName(requestDto.getStudentName());
        admission.setAge(requestDto.getAge());
        admission.setGender(requestDto.getGender());
        admission.setAppliedClass(requestDto.getAppliedClass());
        admission.setStatus(AdmissionStatus.PENDING);
        admission.setIsOffline(true);

        admission = admissionRepository.save(admission);

        return AdmissionMapper.toResponse(admission);
    }

    @Override
    public AdmissionResponseDto approveAdmission(Long admissionId, String approvedBy) {

        Admission admission = admissionRepository.findById(admissionId)
                .orElseThrow(() -> new RuntimeException("Admission not found"));

        
        List<ClassSection> sections = classSectionRepository.findAvailableSections(admission.getAppliedClass());

        if (sections.isEmpty()) {
            throw new RuntimeException("No seats available for class " + admission.getAppliedClass());
        }

        
        ClassSection section = sections.get(0);

     
        if (section.getCurrentStrength() >= section.getMaxStrength()) {
            throw new RuntimeException("Class section is full");
        }

       
        String admissionNumber = generateAdmissionNumber();

   
        admission.setStatus(AdmissionStatus.APPROVED);
        admission.setAdmissionNumber(admissionNumber);
        admission.setClassSection(section);
        admissionRepository.save(admission);

      
        Student student = new Student();
        student.setStudentName(admission.getStudentName());
        student.setAdmissionNumber(admissionNumber);
        student.setClassSection(section);
        studentRepository.save(student);

      
        section.setCurrentStrength(section.getCurrentStrength() + 1);
        classSectionRepository.save(section);

       
        auditService.recordAction(admission, "APPROVED", approvedBy);

        return AdmissionMapper.toResponse(admission);
    }




    public Admission getLatestAdmissionForUser(Long userId) {
        List<Admission> admissions = admissionRepository.findByUser_Id(userId);

        if (admissions.isEmpty()) {
            return null; // new student
        }

        // Return latest by ID (or by createdDate if you have it)
        return admissions
                .stream()
                .max(Comparator.comparing(Admission::getId))
                .orElse(null);
    }


    @Override
    public AdmissionResponseDto rejectAdmission(Long admissionId, String rejectedBy) {
        Admission admission = admissionRepository.findById(admissionId)
                .orElseThrow(() -> new RuntimeException("Admission not found"));

        admission.setStatus(AdmissionStatus.REJECTED);
        admissionRepository.save(admission);

        auditService.recordAction(admission, "REJECTED", rejectedBy);

        return AdmissionMapper.toResponse(admission);
    }

    @Override
    public List<AdmissionResponseDto> getAdmissionByStatus(AdmissionStatus status) {
        return admissionRepository.findByStatus(status)
                .stream()
                .map(AdmissionMapper::toResponse)
                .collect(Collectors.toList());
    }
    public List<AdmissionResponseDto> getApprovedAdmissionsForUser(Long userId, String role) {
        if ("ADMIN".equalsIgnoreCase(role)) {
           
            return getAdmissionByStatus(AdmissionStatus.APPROVED);
        } else {
            
            return admissionRepository.findByStatusAndUserId(AdmissionStatus.APPROVED, userId)
                    .stream()
                    .map(AdmissionMapper::toResponse)
                    .collect(Collectors.toList());
        }
    }

    private String generateAdmissionNumber() {
    	long approvedCount = admissionRepository.countByStatus(AdmissionStatus.APPROVED) + 1;
        return String.format("ADM-%d-%05d", java.time.Year.now().getValue(), approvedCount);
    }
    
}
