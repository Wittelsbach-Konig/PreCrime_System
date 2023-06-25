package ru.itmo.PreCrime.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.telegram.telegrambots.meta.api.objects.Video;

import ru.itmo.PreCrime.model.Vision;

@Repository
public interface VisionsRepository extends JpaRepository<Vision, Long>{
    List<Vision> findByAcceptedFalse();
    List<Vision> findByAcceptedTrue();
    Optional<Video> findFirstByAcceptedTrueOrderByIdDesc();
}
