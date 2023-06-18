package ru.itmo.PreCrime.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import ru.itmo.PreCrime.model.User;
import ru.itmo.PreCrime.service.RegistrationService;

@Controller
public class LoginController {
    private final RegistrationService registrationService;

    @Autowired
    public LoginController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "/auth/login";
    }

    @GetMapping("/registration")
    public String getRegistrationPage(@ModelAttribute("user") User user) {
        return "/auth/registration";
    }

    @PostMapping("/registration")
    public String perfectRegistration(@ModelAttribute("user") @Valid User user) {
        registrationService.register(user);
        return "/auth/login";
    }
    
}
