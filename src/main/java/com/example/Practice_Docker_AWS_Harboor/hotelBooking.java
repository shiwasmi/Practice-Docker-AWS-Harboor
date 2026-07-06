package com.example.Practice_Docker_AWS_Harboor;

import org.springframework.web.bind.annotation.GetMapping;

public class hotelBooking {
    @GetMapping("/hotelBooking")
    public String getName() {
        return "Book your hotel Booking as soon as possible.";
    }
}
