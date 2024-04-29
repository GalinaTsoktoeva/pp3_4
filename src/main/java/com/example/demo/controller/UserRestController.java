package com.example.demo.controller;

import com.example.demo.dto.RegistrationUserDto;
import com.example.demo.dto.UserDto;
import com.example.demo.model.User;
import com.example.demo.service.AuthService;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "*")
public class UserRestController {
    private final UserService userService;
    private final AuthService authService;


    @GetMapping("/inf0")
    public String infoData(Principal principal) {
        return principal.getName();
    }


    @GetMapping("/admin/users")
    public List<UserDto> findAll() {
        List<User> users = userService.findAll();
        return users.stream().map(
                user -> new UserDto(user.getId(),
                        user.getFirstname(),
                        user.getLastname(),
                        user.getUsername(),
                        user.getEmail(),
                        user.getRoles()
                )).collect(Collectors.toList());
    }

    @PostMapping("/user-create")
    public ResponseEntity<?> createNewUser(@RequestBody RegistrationUserDto registrationUserDto) throws Exception {
        return authService.createNewUser(registrationUserDto);
    }

    @GetMapping("/user-delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
        try {
            if (!userService.existsById(id)) {
                return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
            }
            userService.deleteById(id);
            return new ResponseEntity<>("User deleted", HttpStatus.OK) ;

        } catch (Exception e) {
            return new ResponseEntity<>("Failed to delete user", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/user-update")
    public ResponseEntity<?> updateUser( @RequestParam("user") User user) {
        userService.saveUser(user);

        return new ResponseEntity<>("user updated", HttpStatus.OK);
    }
//    @GetMapping("/user-create")
//    public String createUserForm(User user) {
//        return "user-create";
//    }
//    @PostMapping("/user-create")
//    public String createUser(User user) {
//        userService.saveUser(user);
//        return "redirect:/users";
//    }
//    @GetMapping("/user-delete/{id}")
//    public String deleteUser(@PathVariable("id") Long id){
//        userService.deleteById(id);
//        return "redirect:/users";
//    }
//    @GetMapping("/user-update/{id}")
//    public String updateUserForm(@PathVariable("id") Long id, Model model) {
//        User user = userService.findById(id);
//        model.addAttribute("user", user);
//        return "/user-update";
//    }
//    @PostMapping("/user-update")
//    public String updateUser(User user) {
//        userService.saveUser(user);
//        return "redirect:/users";
//    }
}
