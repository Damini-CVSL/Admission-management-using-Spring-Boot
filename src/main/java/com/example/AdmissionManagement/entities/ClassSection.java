package com.example.AdmissionManagement.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Generated;

@Entity
@Table(name = "class_sections")
public class ClassSection {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	
	private String className;
	
	private String section;
	
	private Integer maxStrength;
	
	private Integer currentStrength;
	
	@OneToMany(mappedBy = "classSection", fetch = FetchType.LAZY)
	private List<Student> students = new ArrayList<>();
	
	 @OneToMany(mappedBy = "classSection", fetch = FetchType.LAZY)
	 private List<Admission> admissions = new ArrayList<>();
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public Integer getMaxStrength() {
		return maxStrength;
	}

	public void setMaxStrength(Integer maxStrength) {
		this.maxStrength = maxStrength;
	}

	public Integer getCurrentStrength() {
		return currentStrength;
	}

	public void setCurrentStrength(Integer curentStrength) {
		this.currentStrength = curentStrength;
	}

	public List<Student> getStudents() {
		return students;
	}

	public void setStudents(List<Student> students) {
		this.students = students;
	}

	


}
