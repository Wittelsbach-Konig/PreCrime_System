package ru.itmo.PreCrime.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import jakarta.validation.Valid;

import ru.itmo.PreCrime.model.CrimeCard;
import ru.itmo.PreCrime.service.FillingCardService;

@Controller
public class CardController {
    
    private final FillingCardService cardsService;

    @Autowired
    public CardController(FillingCardService cardsService) {
        this.cardsService = cardsService;
    }

    @GetMapping("/cardfill")
    public String getCardFillPage(@ModelAttribute("crimecard") CrimeCard card) {
        return "cardfill";
    }

    @PostMapping("/cardfill")
    public String perfectFilling(@Valid @ModelAttribute("crimecard") CrimeCard card, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "cardfill";
        }

        cardsService.saveCard(card);
        return "police_office";
    }
}
