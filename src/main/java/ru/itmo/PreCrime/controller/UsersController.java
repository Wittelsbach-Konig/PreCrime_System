package ru.itmo.PreCrime.controller;

import java.util.Optional;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import ru.itmo.PreCrime.model.Role;
import ru.itmo.PreCrime.model.User;
import ru.itmo.PreCrime.security.UserDetailsImpl;
import ru.itmo.PreCrime.service.UsersService;
import ru.itmo.PreCrime.util.UserValidator;

@Controller
public class UsersController {

    private final UserValidator userValidator;
    private final UsersService registrationService;
    
    @Autowired
    public UsersController(UserValidator userValidator, UsersService registrationService) {
        this.userValidator = userValidator;
        this.registrationService = registrationService;
    }

    @GetMapping("/cabinet")
    public String cabinet(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        Optional<? extends GrantedAuthority> authorityOptional = userDetails.getAuthorities().stream().findFirst();
        if (authorityOptional.isPresent()) {
            GrantedAuthority authority = authorityOptional.get();
            if (authority.getAuthority().equals(Role.DETECTIVE.toString())) {
                return "police_office";
            } else if (authority.getAuthority().equals(Role.TECHNIC.toString())) {
                return "temple";
            }
        }
        return "error";
    }

    @GetMapping("/registration")
    public String getRegistrationPage(@ModelAttribute("user") User user) {
        return "/auth/registration";
    }

    @GetMapping("/auth/login")
    public String getLoginPage() {
        return "/auth/login";
    }

    @PostMapping("/registration")
    public String perfectRegistration(@ModelAttribute("user") @Valid User user,
                                      BindingResult bindingResult) {
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return "auth/registration";
        }
        registrationService.register(user);
        return "/auth/login";

    }
}
