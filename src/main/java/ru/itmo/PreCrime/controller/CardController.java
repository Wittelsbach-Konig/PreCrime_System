package ru.itmo.PreCrime.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import jakarta.validation.Valid;

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
    private final UsersRepository usersRepository;

    @Autowired
    public CardController(FillingCardService cardsService,
                          CardsRepository cardsRepository,
                          TelegramBotService telegramBot,
                          UsersRepository usersRepository) {

        this.cardsService = cardsService;
        this.cardsRepository = cardsRepository;
        this.telegramBot = telegramBot;
        this.usersRepository = usersRepository;
    }

    @GetMapping("/cardfill")
    public String getCardFillPage(@ModelAttribute("crimecard") CrimeCard card) {
        return "cardfill";
    }

    @PostMapping("/cardfill")
    public String perfectFilling(@Valid @ModelAttribute("crimecard") CrimeCard card, BindingResult bindingResult, @ModelAttribute("user") @Valid User user) {
        if (bindingResult.hasErrors()) {
            return "cardfill";
        }

        cardsService.saveCard(card);
        CrimeCard lastCard = cardsRepository.findTopByOrderByIdDesc();
        String message = "Андрюха кажется будет труп, возможно криминал, по коням:\n" +
                         "Убийца: " + lastCard.getCriminal_name() + "\n" +
                         "Жертва: " + lastCard.getVictim_name() + "\n" +
                         "Место убийства: " + lastCard.getPlaceofcrime() + "\n";
        int chatId = user.getTelegramId(); // 433915408
        telegramBot.sendMessage(chatId, message);
                        
        return "redirect:/cabinet";
    }

    @ModelAttribute
    private void addAtributes(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        model.addAttribute("user", userDetails.getUser());
    }
}
