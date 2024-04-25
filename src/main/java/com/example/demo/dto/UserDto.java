package com.example.demo.dto;

import com.example.demo.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Collection;
import java.util.Set;

@Data
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String name;
    private String lastname;
    private String username;
    private String email;
    private Collection<Role> roles;
}
