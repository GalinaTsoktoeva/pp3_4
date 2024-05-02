package com.example.demo.dto;

import com.example.demo.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Collection;


@Data
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String name;
    private String lastname;
    private String username;
    private String email;
    private String password;
    private Collection<Role> roles;
}
