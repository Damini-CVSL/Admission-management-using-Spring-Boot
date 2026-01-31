package com.example.AdmissionManagement.mappers;

import com.example.AdmissionManagement.dtos.UserRequestDto;
import com.example.AdmissionManagement.dtos.UserResponseDto;
import com.example.AdmissionManagement.entities.Role;
import com.example.AdmissionManagement.entities.User;

public class UserMapper {

    public static UserResponseDto toResponse(User user) {
        if (user == null) return null;
        return new UserResponseDto(user.getId(), user.getUserName(), user.getRole());
    }

    public static User toEntity(UserRequestDto dto) {
        if (dto == null) return null;
        User user = new User();
        user.setUserName(dto.getUserName());
        user.setPassword(dto.getPassword()); // plain text
        try {
            user.setRole(Role.valueOf(dto.getRole().toUpperCase()));
        } catch (Exception e) {
            user.setRole(Role.STUDENT);
        }
        return user;
    }
}
