package com.example.demo.service;

import com.example.demo.dto.RegistrationUserDto;
import com.example.demo.dto.UserDto;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User findById(Long id){
        return userRepository.getReferenceById(id);
    }
    public User saveUser(User user){

        Optional<Role> role = roleRepository.findById(1L);
        role.ifPresent(value -> user.setRoles(Collections.singleton(value)));
//        user.setPassword();
        return userRepository.save(user);
    }
    public List<User> findAll(){
        return userRepository.findAll();
    }

    public void deleteById(Long id){
        userRepository.deleteById(id);
    }

    public boolean existsById(Long id){ return userRepository.existsById(id); }

    public List<Role> findAllRoles() { return roleRepository.findAll(); }

    @Override
//    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(
                String .format("Плдьзователь '%s' не найден", username)));

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList()));


    }

    public User createNewUser(RegistrationUserDto registrationUserDto){
        User user = new User();
        user.setFirstname(registrationUserDto.getName());
        user.setLastname(registrationUserDto.getLastname());
        user.setUsername(registrationUserDto.getUsername());
        user.setEmail(registrationUserDto.getEmail());
        user.setPassword(passwordEncoder.encode(registrationUserDto.getPassword()));
        user.setRoles(List.of(roleRepository.findByName("ROLE_USER").get()));
        return userRepository.save(user);
    }

    public User updateUser(UserDto userDto) {
        User user = findById(userDto.getId());

        if (userDto.getName() != null && !userDto.getName().equals(user.getFirstname())) {
            user.setFirstname(userDto.getName());
        }
        if (userDto.getLastname() != null && !userDto.getLastname().equals(user.getLastname())) {
            user.setLastname(userDto.getLastname());
        }
        if (userDto.getUsername() != null && !userDto.getUsername().equals(user.getUsername())) {
            user.setUsername(userDto.getUsername());
        }
        if (userDto.getEmail() != null && !userDto.getEmail().equals(user.getEmail())) {
            user.setEmail(userDto.getEmail());
        }
        if (userDto.getRoles() != null) {
            addMissingRoles(user, userDto.getRoles());
        }

        if (user.getPassword() != null && !user.getPassword().equals(userDto.getPassword())) {
            if (!passwordEncoder.matches(userDto.getPassword(), user.getPassword())) {
                user.setPassword(passwordEncoder.encode(userDto.getPassword()));
            }
        }


//        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
//            user.setRoles(List.of(roleRepository.findByName(userDto.getRoles())));
            return userRepository.save(user);

    }

    public void addMissingRoles(User user, Collection<Role> checkRoles) {
        Collection<Role> roles = user.getRoles();
        for (Role role : checkRoles) {
            if (!roles.contains(role)) {
                Optional<Role> current_role = roleRepository.findByName(role.getName());
                user.getRoles().add(current_role.get());
            }
        }
    }
}
