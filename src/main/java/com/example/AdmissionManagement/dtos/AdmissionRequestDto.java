package com.example.AdmissionManagement.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class AdmissionRequestDto {

    @NotBlank(message = "Student name is required")
    private String studentName;

    @Min(value = 3, message = "Age must be at least 3")
    private int age;

    @NotBlank(message = "Gender is required")
    private String gender;

    @NotBlank(message = "Applied class is required")
    private String appliedClass;

    private boolean offline;

    // Default constructor
    public AdmissionRequestDto() {}

    // Getters & Setters
    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getAppliedClass() { return appliedClass; }
    public void setAppliedClass(String appliedClass) { this.appliedClass = appliedClass; }

    public boolean isOffline() { return offline; }
    public void setOffline(boolean offline) { this.offline = offline; }
}
