package ru.itmo.PreCrime.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

@RestController
public class RandomDateTimeController {
    @GetMapping("/randomDateTime")
    public String getRandomDateTime() {
        Random random = new Random();
        long timestamp = System.currentTimeMillis() + random.nextInt(260000000); // случайный таймстамп в пределах последних 1000000000 миллисекунд
        Date date = new Date(timestamp);
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
        return sdf.format(date);
    }
}