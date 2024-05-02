package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.CurrentSecurityContext;
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

    @GetMapping("/admin/user-list")
    public String findAll(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> user = userService.findByUsername(auth.getName());
        model.addAttribute("allRoles", userService.findAllRoles());
        if (!model.containsAttribute("user")) {
            model.addAttribute("user", new User());
        }
        model.addAttribute("userAuth",user);

        if (user.isEmpty()) System.out.println("User is null");

        List<User> users = userService.findAll();

        return "user-list";
    }

    @GetMapping("/admin/{id}/profile")
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

    @GetMapping("/user-info")
    public String userInfo(@CurrentSecurityContext(expression = "authentication.principal") User principal, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(auth.getName()).get();
        model.addAttribute("user", user);
//        model.addAttribute("allRoles", userService.findAllRoles());
        return "fragments/user-info";
    }
}
