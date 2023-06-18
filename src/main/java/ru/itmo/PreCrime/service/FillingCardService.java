package ru.itmo.PreCrime.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import ru.itmo.PreCrime.model.CrimeCard;
import ru.itmo.PreCrime.repository.CardsRepository;


public class FillingCardService {
    private final CardsRepository cardRepository;

    @Autowired
    public FillingCardService(CardsRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @Transactional
    public void saveCard(CrimeCard card) {
        //userRepository.save(user);
        cardRepository.save(card);
    }
}
