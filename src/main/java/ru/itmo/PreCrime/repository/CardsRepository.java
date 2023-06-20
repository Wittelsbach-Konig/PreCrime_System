package ru.itmo.PreCrime.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ru.itmo.PreCrime.model.CrimeCard;

import java.util.List;

@Repository
public interface CardsRepository extends JpaRepository<CrimeCard, Long> {
    List <CrimeCard> findAllByOrderByIdDesc();

    CrimeCard findTopByOrderByIdDesc();
}

