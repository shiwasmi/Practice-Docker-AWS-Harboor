package com.example.Practice_Docker_AWS_Harboor;

import org.springframework.web.bind.annotation.GetMapping;

public class fightBooking {
    @GetMapping("/fightBooking")
    public String getName() {
        return "Book your fight as soon as possible.";
    }
}
