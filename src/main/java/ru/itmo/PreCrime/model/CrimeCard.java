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
    private Integer id;

    @Enumerated(EnumType.STRING)
    private CrimeType typecrime;

    private String victim_name;
    private String criminal_name;
    private String placeofcrime;
    private LocalDateTime crimetime;
    private String weapon;
}
