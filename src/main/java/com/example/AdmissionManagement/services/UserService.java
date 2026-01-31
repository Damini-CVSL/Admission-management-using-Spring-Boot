package com.example.AdmissionManagement.services;

import com.example.AdmissionManagement.dtos.UserRequestDto;
import com.example.AdmissionManagement.dtos.UserResponseDto;

public interface UserService {
    UserResponseDto register(UserRequestDto requestDto);
    UserResponseDto login(String username, String password);
}
