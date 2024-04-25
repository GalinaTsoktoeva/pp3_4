package com.example.demo.dto;

import com.example.demo.model.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Collection;

@Data
@AllArgsConstructor
public class RegistrationUserDto {

    private String name;

    private String lastname;

    private String username;

    private String password;

    private String confirmPassword;

    private String email;
}
