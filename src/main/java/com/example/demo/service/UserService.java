package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public User findById(Long id){
        return userRepository.getOne(id);
    }
    public User saveUser(User user){
        return userRepository.save(user);
    }
    public List<User> findAll(){
        return userRepository.findAll();
    }
    public void deleteById(Long id){
        userRepository.deleteById(id);
    }
}
