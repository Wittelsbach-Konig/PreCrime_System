package ru.itmo.PreCrime.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;
import ru.itmo.PreCrime.model.CrimeCard;
// import ru.itmo.PreCrime.model.User;
import ru.itmo.PreCrime.service.FillingCardService;

public class CardController {
    
    private final FillingCardService cardsService;

    @Autowired
    public CardController(FillingCardService cardsService) {
        this.cardsService = cardsService;
    }



    @GetMapping("/cardfill")
    public String getCardFillPage(@ModelAttribute("crimecard") CrimeCard card) {
        return "/card_fill";
    }

    @PostMapping("/cardfill")
    public String perfectFilling(@ModelAttribute("crimecard") CrimeCard card) {
        //registrationService.register(user);
        cardsService.saveCard(card);
        return "/police_office";
    }
}
