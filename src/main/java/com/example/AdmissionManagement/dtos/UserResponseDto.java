package com.example.AdmissionManagement.dtos;

import com.example.AdmissionManagement.entities.Role;

public record UserResponseDto(
        Long id,
        String username,
        Role role
) {
};

