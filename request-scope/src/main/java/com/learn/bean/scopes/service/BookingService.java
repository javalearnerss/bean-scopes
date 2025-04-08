package com.learn.bean.scopes.service;

import com.learn.bean.scopes.model.UserContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookingService {
    private static Logger logger = LoggerFactory.getLogger(BookingService.class);

    @Autowired
    private UserContext userContext;

    public String bookTicket() {
        String eventId = "Ticket Booking";
        logger.info("User context object in booking service {}",userContext);
        // ticket booking logic goes here
        logger.info("Ticket booked for event: {}  by user: {} ",eventId, userContext.getName());
        return "Ticket booked successfully for user: " + userContext.getName();
    }
}
