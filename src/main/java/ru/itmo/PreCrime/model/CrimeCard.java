package ru.itmo.PreCrime.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "crimecard")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CrimeCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String victim_name;
    private String criminal_name;
    private String placeofcrime;
    private String weapon;
    private LocalDateTime crimetime;
    @Enumerated(EnumType.STRING)
    private CrimeType typecrime;
    @Builder.Default
    private Boolean approve = false;
}
