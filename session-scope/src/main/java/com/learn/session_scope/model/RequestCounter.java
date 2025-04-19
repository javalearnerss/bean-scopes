package com.learn.session_scope.model;

import jakarta.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class RequestCounter {

    private final Logger logger = LoggerFactory.getLogger(RequestCounter.class);
    private String username;      // Stores the logged-in user's name
    private AtomicInteger requestCount;   // Tracks how many times the user interacted
    private final String id;            // Unique bean ID

    public RequestCounter() {
        id = UUID.randomUUID().toString();
        requestCount = new AtomicInteger(0);
        logger.info("RequestCounter object is created , ID : {}", id);
    }

    public String getId() {
        return id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public int incrementCountAndGet() {
        return requestCount.incrementAndGet();
    }

    public int getRequestCount() {
        return requestCount.get();
    }

    @Override
    public String toString() {
        return "RequestCounter{" +
                "username='" + username + '\'' +
                ", requestCount=" + requestCount +
                ", id='" + id + '\'' +
                '}';
    }

    @PreDestroy
    public void destroy() {
        logger.info("RequestCounter object is destroyed, ID : {}", this.id);
    }
}