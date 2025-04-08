package com.learn.bean.scopes.service;
import com.learn.bean.scopes.model.UserContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private static Logger logger = LoggerFactory.getLogger(NotificationService.class);

    @Autowired
    private UserContext userContext;

    public String sendConfirmation() {
        String eventId = "Ticket Booking";
        logger.info("User context object in notification service {}",userContext);
        // Send notification logic goes here
        logger.info("Confirmation sent to user: {} for event: {}",userContext.getName(), eventId);
        return "Ticket is booked for user: " + userContext.getName();
    }
}