package ru.itmo.PreCrime.util;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import ru.itmo.PreCrime.model.Role;
import ru.itmo.PreCrime.model.User;
import ru.itmo.PreCrime.repository.UsersRepository;

@Component
public class DbInitializer {

    private final UsersRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DbInitializer(UsersRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
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

}
