package com.owpk.subscription.controller;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.owpk.subscription.jpa.AppUser;
import com.owpk.subscription.jpa.UserRepository;

@Controller
@RequestMapping("/users")
public class UserManagementController {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    public UserManagementController(UserRepository userRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    @GetMapping
    public String form(Model model) {
        model.addAttribute("user", new AppUser());
        return "user-form";
    }

    @PostMapping
    public String create(@ModelAttribute AppUser user) {
        user.setPassword(encoder.encode(user.getPassword()));
        user.setRole("META_USER");
        userRepository.save(user);
        return "redirect:/subscriptions";
    }
}
