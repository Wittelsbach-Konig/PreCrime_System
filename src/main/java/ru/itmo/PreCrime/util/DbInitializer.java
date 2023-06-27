package ru.itmo.PreCrime.util;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.CachedIntrospectionResults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Example;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import ru.itmo.PreCrime.model.*;
import ru.itmo.PreCrime.repository.CardsRepository;
import ru.itmo.PreCrime.repository.UsersRepository;
import ru.itmo.PreCrime.repository.VisionsRepository;

@Component
public class DbInitializer {

    private final UsersRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final VisionsRepository visionsRepository;
    private final CardsRepository cardsRepository;

    @Autowired
    public DbInitializer(UsersRepository userRepository,
                         PasswordEncoder passwordEncoder,
                         VisionsRepository visionsRepository,
                         CardsRepository cardsRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.visionsRepository = visionsRepository;
        this.cardsRepository = cardsRepository;
    }

    @EventListener(ApplicationStartedEvent.class)
    public void afterStartup() {
		Optional<User> technicUserOptional = userRepository.findByLogin("technic");
        Optional<User> detectiveUserOptional = userRepository.findByLogin("detective");
        Optional<User> reactionGroupUserOptional = userRepository.findByLogin("reactgr");
		Optional<User> auditorUserOptional = userRepository.findByLogin("auditor");

        if(technicUserOptional.isEmpty()) {
            userRepository.save(User.builder()
                    .login("technic")
                    .password(passwordEncoder.encode("technic"))
                    .role(Role.TECHNIC)
                    .firstName("Уолли")
                    .lastName("Уоллес")
                    .build());
        }
        if(detectiveUserOptional.isEmpty()) {
            userRepository.save(User.builder()
                    .login("detective")
                    .password(passwordEncoder.encode("detective"))
                    .role(Role.DETECTIVE)
                    .firstName("Том")
                    .lastName("Круз")
                    .telegramId(433915408)
                    .build());
        }
        if(reactionGroupUserOptional.isEmpty()) {
            userRepository.save(User.builder()
                    .login("reactgr")
                    .password(passwordEncoder.encode("reactgr"))
                    .role(Role.REACTIONGROUP)
                    .firstName("Сэм")
                    .lastName("Лейк")
                    .build());
        }
		if(auditorUserOptional.isEmpty()) {
            userRepository.save(User.builder()
                    .login("auditor")
                    .password(passwordEncoder.encode("auditor"))
                    .role(Role.AUDITOR)
                    .firstName("Владимир")
                    .lastName("Путин")
                    .build());
        }
    }

    @EventListener(ApplicationStartedEvent.class)
    public void addVisions() {
        Vision vision1 = Vision.builder()
        .videoUrl("https://drive.google.com/file/d/1cF3j_w4_LTManddaLsGZwPH8rSEUXkQE/preview")
        .accepted(false)
        .build();

        Vision vision2 = Vision.builder()
        .videoUrl("https://drive.google.com/file/d/14R_QnKsCRXWdIzlWJoOD3Sq-D4mo4MZj/preview")
        .accepted(false)
        .build();

        Optional<Vision> vision1Optional = visionsRepository.findOne(
                Example.of(vision1)
        );
        Optional<Vision> vision2Optional = visionsRepository.findOne(
                Example.of(vision2)
        );

        if (vision1Optional.isEmpty()) {
            visionsRepository.save(vision1);
        }
        if (vision2Optional.isEmpty()) {
            visionsRepository.save(vision2);
        }
    }

    @EventListener(ApplicationStartedEvent.class)
    public void addCrimeCards() {
        CrimeCard card1 = CrimeCard.builder()
                .victim_name("Staruxa procentshica")
                .criminal_name("Rodion Raskolnikov")
                .placeofcrime("Saint-Petersburg")
                .weapon("Topor")
                .crimetime(LocalDateTime.of(1914, 12, 31, 12, 15))
                .typecrime(CrimeType.INTENTIONAL)
                .build();

        CrimeCard card2 = CrimeCard.builder()
                .victim_name("Andriy Bulba")
                .criminal_name("Taras Bulba")
                .placeofcrime("Zaporoj'e")
                .weapon("Riffle")
                .crimetime(LocalDateTime.of(1637, 3, 8, 14, 35))
                .typecrime(CrimeType.INTENTIONAL)
                .build();

        Optional<CrimeCard> card1Optional = cardsRepository.findOne(
                Example.of(card1)
        );
        Optional<CrimeCard> card2Optional = cardsRepository.findOne(
                Example.of(card2)
        );

        if (card1Optional.isEmpty()) {
            cardsRepository.save(card1);
        }
        if (card2Optional.isEmpty()) {
            cardsRepository.save(card2);
        }
    }

}
