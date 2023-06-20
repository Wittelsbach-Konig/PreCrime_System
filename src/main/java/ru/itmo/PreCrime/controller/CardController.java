package ru.itmo.PreCrime.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import jakarta.validation.Valid;
import org.springframework.ui.Model;
import java.util.List;

import ru.itmo.PreCrime.model.CrimeCard;
import ru.itmo.PreCrime.repository.CardsRepository;
import ru.itmo.PreCrime.service.FillingCardService;

@Controller
public class CardController {
    
    private final FillingCardService cardsService;
    private final CardsRepository cardsRepository;

    @Autowired
    public CardController(FillingCardService cardsService, CardsRepository cardsRepository) {
        this.cardsService = cardsService;
        this.cardsRepository = cardsRepository;
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

    @GetMapping("/cardslist")
    public String getCards(Model model){
        List <CrimeCard> cards = cardsRepository.findAllByOrderByIdDesc();
        model.addAttribute("cards", cards);
        return "cardslist";
    }

    @GetMapping("/cards/{id}")
    public String getCard(@PathVariable Long id, Model model) {
        CrimeCard card = cardsRepository.findById(id).orElse(null);
        model.addAttribute("card", card);
        return "cardview";
    }

}
