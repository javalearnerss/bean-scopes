package com.learn.bean.scopes.controller;

import com.learn.bean.scopes.service.NotificationService;
import com.learn.bean.scopes.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookingController {

    @Autowired
    private BookingService ticketBookingService;

    @Autowired
    private NotificationService notificationService;


    @GetMapping("/bookTicket")
    public ResponseEntity<String> bookTicket() {
        ticketBookingService.bookTicket();
        notificationService.sendConfirmation();
        return ResponseEntity.ok("Ticket booked successfully");
    }
}
