package ru.itmo.PreCrime.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.telegram.telegrambots.meta.api.objects.Video;

import ru.itmo.PreCrime.model.Vision;
import ru.itmo.PreCrime.repository.VisionsRepository;

@Controller
public class VisionController {
    
    @Autowired
    private VisionsRepository visionsRepository;

    @GetMapping("/visionlist")
    public String getVisionsPage(Model model) {
        List<Vision> approvedVisions = visionsRepository.findByAcceptedFalse();
        model.addAttribute("visions", approvedVisions);
        return "visionslist";
    }

    @PostMapping("/visions/{id}/accept")
    public String acceptVision(@PathVariable("id") Long id) {
        Optional<Vision> visionOptional = visionsRepository.findById(id);
        if (visionOptional.isPresent()) {
            Vision vision = visionOptional.get();
            vision.setAccepted(true);
            visionsRepository.save(vision);
        }
        return "redirect:/visionlist";
    }
}
