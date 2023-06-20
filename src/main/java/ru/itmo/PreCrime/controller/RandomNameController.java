package ru.itmo.PreCrime.controller;

import java.util.Random;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RandomNameController {
    private String[] names = {"John", "Emma", "Michael", "Sophia", "William", "Adolph", "Stepam", "Roman"};
    private String[] names1 = {"Sergey", "Bill", "Alex", "Jane", "Alice", "Diana", "Elizabeth", "Clara", "Jack"};

    @GetMapping("/randomCriminalName")
    public String getRandomCriminalName() {
        Random random = new Random();
        int index = random.nextInt(names.length);
        return names[index];
    }

    @GetMapping("/randomVictimName")
    public String getRandomVictimName() {
        Random random = new Random();
        int index = random.nextInt(names1.length);
        return names1[index];
    }

}

