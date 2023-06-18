package ru.itmo.PreCrime.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import ru.itmo.PreCrime.model.Role;
import ru.itmo.PreCrime.security.UserDetailsImpl;

@Controller
public class CabinetController {
    
    @GetMapping("/cabinet")
    public String cabinet(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        Optional<? extends GrantedAuthority> authorityOptional = userDetails.getAuthorities().stream().findFirst();
        if (authorityOptional.isPresent()) {
            GrantedAuthority authority = authorityOptional.get();
            if (authority.getAuthority().equals(Role.DETECTIVE.toString())) {
                return "police_office";
                // return "cardfill";
            } else if (authority.getAuthority().equals(Role.TECHNIC.toString())) {
                return "temple";
            }
        }
        return "error";
    }

    @ModelAttribute
    private void addAtributes(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        model.addAttribute("user", userDetails.getUser());
    }
}