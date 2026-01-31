package com.example.AdmissionManagement.controllers;

import com.example.AdmissionManagement.dtos.AdmissionRequestDto;
import com.example.AdmissionManagement.dtos.AdmissionResponseDto;
import com.example.AdmissionManagement.entities.Admission;
import com.example.AdmissionManagement.entities.AdmissionStatus;
import com.example.AdmissionManagement.mappers.AdmissionMapper;
import com.example.AdmissionManagement.repositories.AdmissionRepository;
import com.example.AdmissionManagement.services.AdmissionService;
import jakarta.servlet.http.HttpSession;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller   
@RequestMapping("/admissions")
public class AdmissionController {

    private final AdmissionService admissionService;
    private final AdmissionRepository admissionRepository;

    public AdmissionController(AdmissionService admissionService, AdmissionRepository admissionRepository) {
        this.admissionService = admissionService;
        this.admissionRepository = admissionRepository;
    }

 
    @GetMapping
    public String admissionsPage(HttpSession session, Model model) {

        Long userId = (Long) session.getAttribute("userId");
        String username = (String) session.getAttribute("username");
        String role = session.getAttribute("role") != null
                ? session.getAttribute("role").toString().toUpperCase()
                : "STUDENT";

        model.addAttribute("username", username);
        model.addAttribute("role", role);
        model.addAttribute("admissionRequest", new AdmissionRequestDto());

        // ADMIN VIEW
        if ("ADMIN".equals(role)) {
            model.addAttribute(
                    "pendingAdmissions",
                    admissionService.getAdmissionByStatus(AdmissionStatus.PENDING)
            );
            return "admissions";
        }

        // STUDENT VIEW
        Admission admission = admissionService.getLatestAdmissionForUser(userId);

        if (admission == null) {
            // NEW STUDENT
            model.addAttribute("admissionRequest", new AdmissionRequestDto());
        } else {
            // EXISTING STUDENT
            model.addAttribute("admission", admission);
            model.addAttribute("status", admission.getStatus());
        }

        return "admissions";
    }


    @PostMapping("/online")
    public String applyOnline(
            @ModelAttribute AdmissionRequestDto requestDto,
            HttpSession session) {

        Long userId = (Long) session.getAttribute("userId");
        admissionService.applyOnline(requestDto, userId);
        


        return "redirect:/admissions";
    }

    @PostMapping("/offline")
    public String applyOffline(
            @ModelAttribute AdmissionRequestDto requestDto) {

        admissionService.applyOffline(requestDto);
        return "redirect:/admissions";
    }

//    }
    @PostMapping("/{admissionId}/approve")
    public String approveAdmission(
            @PathVariable Long admissionId,
            HttpSession session,
            RedirectAttributes redirectAttributes) {

        try {
            String approvedBy = (String) session.getAttribute("username");
            admissionService.approveAdmission(admissionId, approvedBy);
            redirectAttributes.addFlashAttribute("successMessage", "Admission approved successfully");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }

        return "redirect:/admissions";
    }
    @GetMapping("/approved")
    public String viewApprovedAdmissions(HttpSession session, Model model) {

        String role = session.getAttribute("role") != null
                ? session.getAttribute("role").toString().toUpperCase()
                : "";

        if (!"ADMIN".equals(role)) {
            return "redirect:/admissions"; // safety
        }

        List<AdmissionResponseDto> approvedAdmissions =
                admissionService.getAdmissionByStatus(AdmissionStatus.APPROVED);

        model.addAttribute("approvedAdmissions", approvedAdmissions);

        return "approved-admissions"; // thymeleaf page
    }




    @PostMapping("/{admissionId}/reject")
    public String rejectAdmission(
            @PathVariable Long admissionId,
            HttpSession session) {

        String rejectedBy = (String) session.getAttribute("username");
        admissionService.rejectAdmission(admissionId, rejectedBy);

        return "redirect:/admissions";
    }
}
