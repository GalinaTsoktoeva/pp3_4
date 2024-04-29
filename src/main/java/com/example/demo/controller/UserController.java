package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/user-list")
    public String findAll(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> user = userService.findByUsername(auth.getName());
        model.addAttribute("allRoles", userService.findAllRoles());
        if (!model.containsAttribute("user")) {
            model.addAttribute("user", new User());
        }
        model.addAttribute("userAuth",user);
        model.addAttribute("showUserProfile",
                model.containsAttribute("user") && ((User) Objects.requireNonNull(model.getAttribute("user"))).getId() == null);
        model.addAttribute("showNewUserForm",
                model.containsAttribute("user") && ((User) Objects.requireNonNull(model.getAttribute("user"))).getId() != null);

        if (user.isEmpty()) System.out.println("User is null");

        List<User> users = userService.findAll();
//        model.addAttribute("users", users);
        return "user-list";
    }

    @GetMapping("/{id}/profile")
    public String showUserProfileModal(@PathVariable("id") Long userId, @RequestParam(name = "action") String action, Model model) {
        try {
            model.addAttribute("allRoles", userService.findAllRoles());
            model.addAttribute("user", userService.findById(userId));
            model.addAttribute("action", action);
            return "fragments/user-form";
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }


//    @PatchMapping()
//    public String updateUser( @ModelAttribute("user") User user) {
//        userService.saveUser(user);
//
//        return "redirect:/user-list";
//    }
}
