package com.example.AdmissionManagement.services;
import org.springframework.stereotype.Service;

import com.example.AdmissionManagement.entities.ClassSection;
import com.example.AdmissionManagement.repositories.ClassSectionRepository;
import com.example.AdmissionManagement.services.ClassSectionService;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ClassSectionServiceImpl implements ClassSectionService {

    private final ClassSectionRepository classSectionRepository;

    public ClassSectionServiceImpl(ClassSectionRepository classSectionRepository) {
        this.classSectionRepository = classSectionRepository;
    }

    @Override
    public ClassSection getAvailableSection(String className) {

        return classSectionRepository.findAll()
                .stream()
                .filter(cs -> cs.getClassName().equalsIgnoreCase(className))
                .filter(cs -> cs.getCurrentStrength() < cs.getMaxStrength())
                .findFirst()
                .orElseThrow(() ->
                        new RuntimeException("No available section for class " + className));
    }

    @Override
    public void IncrementStrength(ClassSection classSection) {
        classSection.setCurrentStrength(classSection.getCurrentStrength() + 1);
        classSectionRepository.save(classSection);
    }
}

