package ru.itmo.PreCrime;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;

import ru.itmo.PreCrime.repository.UsersRepository;
import ru.itmo.PreCrime.model.Role;
import ru.itmo.PreCrime.model.User;

@SpringBootApplication
public class PreCrimeApplication {

	public static void main(String[] args) {
		SpringApplication.run(PreCrimeApplication.class, args);
	}

	private final UsersRepository userRepository;
    private final PasswordEncoder passwordEncoder;

	@Autowired
    public PreCrimeApplication(UsersRepository userRepository, PasswordEncoder passwordEncoder) {
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
                    .firstName("Антоша")
                    .lastName("Пидорас")
                    .build());
        }
        if(detectiveUserOptional.isEmpty()) {
            userRepository.save(User.builder()
                    .login("detective")
                    .password(passwordEncoder.encode("detective"))
                    .role(Role.DETECTIVE)
                    .firstName("Том")
                    .lastName("Круз")
                    .build());
        }
        if(reactionGroupUserOptional.isEmpty()) {
            userRepository.save(User.builder()
                    .login("reactgr")
                    .password(passwordEncoder.encode("reactgr"))
                    .role(Role.REACTIONGROUP)
                    .firstName("Бибба")
                    .lastName("Бобба")
                    .build());
        }
		if(auditorUserOptional.isEmpty()) {
            userRepository.save(User.builder()
                    .login("auditor")
                    .password(passwordEncoder.encode("auditor"))
                    .role(Role.AUDITOR)
                    .firstName("Френк")
                    .lastName("Френк")
                    .build());
        }
    }

}
