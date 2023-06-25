package ru.itmo.PreCrime.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ru.itmo.PreCrime.model.Role;
import ru.itmo.PreCrime.model.User;
import java.util.List;


@Repository
public interface UsersRepository extends JpaRepository<User, Long> {
    public Optional<User> findByLogin(String login);
}
