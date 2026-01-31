package com.example.AdmissionManagement.controllers;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model; // ✅ CORRECT


import com.example.AdmissionManagement.dtos.UserRequestDto;
import com.example.AdmissionManagement.dtos.UserResponseDto;
import com.example.AdmissionManagement.services.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // =========================
    // LOGIN PAGE
    // =========================
    @GetMapping("/login")
    public String loginPage(Model model) {
        // Use empty UserRequestDto for register form
        model.addAttribute("user", new UserRequestDto());
        return "index"; // Thymeleaf renders index.html
    }
    
    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, HttpSession session, Model model) {
        try {
            var result = userService.login(username, password);
            session.setAttribute("userId", result.id());
            session.setAttribute("username", result.username());
            session.setAttribute("role", result.role().name());
            return "redirect:/admissions"; // or your dashboard
        } catch (RuntimeException e) {
            model.addAttribute("error", "Invalid username or password");
            model.addAttribute("user", new UserRequestDto()); // for register form
            return "index";
        }
    }


    // =========================
    // REGISTER PAGE
    // =========================
    @GetMapping("/register")
    public String registerPage(Model model) {
        // Same as login page
        model.addAttribute("user", new UserRequestDto("", "", "STUDENT"));
        return "index"; // reuse index.html
    }

    // =========================
    // REGISTER POST
    // =========================
    @PostMapping("/register")
    public String register(@ModelAttribute UserRequestDto requestDto, Model model) {
        try {
            var response = userService.register(requestDto);
            model.addAttribute("message", "Registered successfully: " + response.username());
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
        }
        model.addAttribute("user", new UserRequestDto());
        return "index";
    }


    // =========================
    // LOGIN POST
    // =========================
    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/users/login";
    }

   
}
