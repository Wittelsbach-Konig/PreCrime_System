package ru.itmo.PreCrime.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.validation.Valid;
import java.util.List;


import ru.itmo.PreCrime.model.CrimeCard;
import ru.itmo.PreCrime.model.User;
import ru.itmo.PreCrime.repository.CardsRepository;
import ru.itmo.PreCrime.repository.UsersRepository;
import ru.itmo.PreCrime.security.UserDetailsImpl;
import ru.itmo.PreCrime.service.FillingCardService;
import ru.itmo.PreCrime.service.TelegramBotService;

@Controller
public class CardController {
    
    private final CardsRepository cardsRepository;
    private final FillingCardService cardsService;
    private final TelegramBotService telegramBot;


    @Autowired
    public CardController(FillingCardService cardsService,
                          CardsRepository cardsRepository,
                          TelegramBotService telegramBot) {

        this.cardsService = cardsService;
        this.cardsRepository = cardsRepository;
        this.telegramBot = telegramBot;
    }

    @PostMapping("/cardfill")
    @ResponseBody
    public CrimeCard createCard(@RequestBody CrimeCard card, @ModelAttribute("user") @Valid User user) {
        // Сохранение карточки в базе данных
        CrimeCard result = cardsRepository.save(card);
        CrimeCard lastCard = cardsRepository.findTopByOrderByIdDesc();
        String message = "Андрюха кажется будет труп, возможно криминал, по коням:\n" +
                    "Id: " + lastCard.getId() + "\n" +
                    "Убийца: " + lastCard.getCriminal_name() + "\n" +
                    "Жертва: " + lastCard.getVictim_name() + "\n" +
                    "Место убийства: " + lastCard.getPlaceofcrime() + "\n";
        int chatId = user.getTelegramId(); // 433915408
        telegramBot.sendMessage(chatId, message);
        return result;
    }

    @ModelAttribute
    private void addAtributes(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        model.addAttribute("user", userDetails.getUser());
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
