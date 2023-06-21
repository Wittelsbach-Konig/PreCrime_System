package ru.itmo.PreCrime.service;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import lombok.extern.slf4j.Slf4j;
import ru.itmo.PreCrime.config.BotConfig;
import ru.itmo.PreCrime.model.CrimeCard;
import ru.itmo.PreCrime.repository.CardsRepository;

@Slf4j
@Component
public class TelegramBotService extends TelegramLongPollingBot{

    final BotConfig config;
    private final CardsRepository cardsRepository;

    static final String ERROR_TEXT = "Error occurred: ";

    static final String HELP_TEXT = "This bot is created to demonstrate Power of PreCrime System.\n\n" +
        "Authors: Kiryushin Vitaliy, Artem Khudyakov, Sergeich Kirill\n\n"+
        "You can execute commands from the main menu on the left or by typing a command:\n\n" +
        "Type /start to see a welcome message\n\n" +
        "Type /help to see this message again";
    
    static final String APPROVE_TEXT = "Уведомление об аресте подтверждено!";

    public TelegramBotService(BotConfig config, CardsRepository cardsRepository) {
        this.config = config;
        this.cardsRepository = cardsRepository;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if(update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();
            if (messageText.contains("/approve")) {
                String[] parts = messageText.split(" ");
                if (parts.length == 2) {
                    try {
                        Long cardId = Long.parseLong(parts[1]);
                        approveCard(cardId);
                        prepareAndSendMessage(chatId, APPROVE_TEXT);
                    }
                    catch (NumberFormatException e) {
                        // Обработка некорректного идентификатора карточки
                    }
                }
                else {
                    // Обработка некорректного формата команды
                }
            }
            else {
                switch (messageText){
                    case "/start":
                        startCommandReceived(chatId, update.getMessage().getChat().getFirstName());
                        break;
                    case "/help":
                        prepareAndSendMessage(chatId, HELP_TEXT);
                        break;
                    default:
                        prepareAndSendMessage(chatId, "Sorry, command was not recognized");
                }
            }
        }
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }

    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    public void sendMessage(long chatId, String textToSend) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);

        executeMessage(message);
    }

    private void startCommandReceived(Long chatId, String name) {
        String answer = "Hi, " + name +", nice to meet you!";
        // log.info("Replied to user " + name);
        sendMessage(chatId, answer);
    }

    private void executeMessage(SendMessage message){
        try {
            execute(message);
        } catch (TelegramApiException e) {
            // log.error(ERROR_TEXT + e.getMessage());
            System.out.printf(e.getMessage());
        }
    }

    private void prepareAndSendMessage(long chatId, String textToSend){
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);
        executeMessage(message);
    }

    private void approveCard(Long cardId) {
        CrimeCard card = cardsRepository.findById(cardId).orElse(null);
        if (card != null) {
            card.setApprove(true);
        }
        cardsRepository.save(card);
    }
}
