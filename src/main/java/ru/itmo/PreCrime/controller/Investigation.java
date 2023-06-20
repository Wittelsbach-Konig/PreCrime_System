package ru.itmo.PreCrime.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class Investigation {
    
    @GetMapping("/investigation")
    public String getInvestigationPage() {
        return "/investigation";
    }
}
