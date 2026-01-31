package com.example.AdmissionManagement.repositories;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.AdmissionManagement.entities.ClassSection;

public interface ClassSectionRepository extends JpaRepository<ClassSection, Long> {

    Optional<ClassSection> findByClassNameAndSection(String className, String section);

    List<ClassSection> findByClassName(String className);

    @Query("""
    		SELECT cs FROM ClassSection cs
    		WHERE cs.className = :className
    		ORDER BY cs.currentStrength ASC
    		""")
    List<ClassSection> findAvailableSections(@Param("className") String className);
}


//package com.example.AdmissionManagement.repositories;
//
//import java.util.Optional;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//
//import com.example.AdmissionManagement.entities.ClassSection;
//
//public interface ClassSectionRepository extends JpaRepository<ClassSection, Long> {
//
//
//    // 🔹 Case-insensitive lookup
//    @Query("""
//        SELECT cs FROM ClassSection cs
//        WHERE LOWER(cs.className) = LOWER(:className)
//        ORDER BY cs.currentStrength ASC
//    """)
//    Optional<ClassSection> findAvailableSection(@Param("className") String className);
//}
