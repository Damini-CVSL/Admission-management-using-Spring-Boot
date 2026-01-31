package com.example.AdmissionManagement.services;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.AdmissionManagement.dtos.UserRequestDto;
import com.example.AdmissionManagement.dtos.UserResponseDto;
import com.example.AdmissionManagement.entities.Role;
import com.example.AdmissionManagement.entities.User;
import com.example.AdmissionManagement.exception.InvalidCredentialsException;
import com.example.AdmissionManagement.exception.UserAlreadyExistsException;
import com.example.AdmissionManagement.repositories.UserRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserResponseDto register(UserRequestDto requestDto) {

        // Check if username already exists
        if (userRepository.existsByUserName(requestDto.getUserName())) {
            throw new UserAlreadyExistsException("Username already exists");
        }

        // Create new User entity
        User user = new User();
        user.setUserName(requestDto.getUserName());
        user.setPassword(requestDto.getPassword()); // plain text

        // Validate role, default to STUDENT if invalid
        try {
            user.setRole(Role.valueOf(requestDto.getRole().toUpperCase()));
        } catch (Exception e) {
            user.setRole(Role.STUDENT); // Default role
        }

        User savedUser = userRepository.save(user);

        // Return DTO
        return new UserResponseDto(
                savedUser.getId(),
                savedUser.getUserName(),
                savedUser.getRole()
        );
    }

    @Override
    public UserResponseDto login(String username, String password) {

        User user = userRepository.findByUserName(username)
                .orElseThrow(() -> new RuntimeException("Invalid username or password"));

        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("Invalid username or password");
        }
        System.out.println("USERNAME: " + username);
        System.out.println("PASSWORD: " + password);

        return new UserResponseDto(
                user.getId(),
                user.getUserName(),
                user.getRole()
        );
    }

}
