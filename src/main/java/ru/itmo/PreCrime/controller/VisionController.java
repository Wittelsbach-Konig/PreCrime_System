package ru.itmo.PreCrime.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import ru.itmo.PreCrime.model.Notification;
import ru.itmo.PreCrime.model.Role;
import ru.itmo.PreCrime.model.User;
import ru.itmo.PreCrime.model.Vision;
import ru.itmo.PreCrime.repository.UsersRepository;
import ru.itmo.PreCrime.repository.VisionsRepository;
import ru.itmo.PreCrime.security.UserDetailsImpl;

@Controller
public class VisionController {
    
    @Autowired
    private VisionsRepository visionsRepository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private UsersRepository usersRepository;

    private Notification latestNotification; // Последнее уведомление

    @GetMapping("/visionslist")
    public String getVisionsPage(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Optional<? extends GrantedAuthority> authorityOptional = userDetails.getAuthorities().stream().findFirst();
        if (authorityOptional.isPresent()) {
            GrantedAuthority authority = authorityOptional.get();
            if (authority.getAuthority().equals(Role.DETECTIVE.toString())) {
                List<Vision> approvedVisions = visionsRepository.findByAcceptedTrue();
                model.addAttribute("visions", approvedVisions);
            } 
            else if (authority.getAuthority().equals(Role.TECHNIC.toString())) {
                List<Vision> approvedVisions = visionsRepository.findByAcceptedFalse();
                model.addAttribute("visions", approvedVisions);
            }
            return "visionslist";
        }
        return "error";
    }

    @ModelAttribute
    private void addAtributes(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        model.addAttribute("user", userDetails.getUser());
        model.addAttribute("TECHNIC", Role.TECHNIC);
    }

    

    @PostMapping("/visions/{id}/accept")
    public String acceptVision(@PathVariable("id") Long id) {
        Optional<Vision> visionOptional = visionsRepository.findById(id);
        if (visionOptional.isPresent()) {
            Vision vision = visionOptional.get();
            vision.setAccepted(true);
            visionsRepository.save(vision);
            Notification notification = new Notification();
            notification.setMessage("Новое видение для просмотра Id = " + vision.getId());
            latestNotification = notification;
            messagingTemplate.convertAndSend("/topic/notifications", notification);
        }
        return "redirect:/visionslist";
    }

    @DeleteMapping("/visions/{id}")
    public ResponseEntity<String> deleteVision(@PathVariable Long id) {
        Optional<Vision> visionOptional = visionsRepository.findById(id);
        if (visionOptional.isPresent()) {
            Vision vision = visionOptional.get();
            visionsRepository.delete(vision);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @MessageMapping("/notifications/latest") // Запрос на получение последнего уведомления
    @SendTo("/topic/notifications")
    public Notification getLatestNotification() {
        return latestNotification;
    }
}
