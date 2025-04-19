package com.learn.session_scope.controller;

import com.learn.session_scope.model.RequestCounter;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RequestCounterController {

    private final Logger logger = LoggerFactory.getLogger(RequestCounterController.class);

    @Autowired
    private RequestCounter requestCounter;

    @GetMapping("/protected/get-request-info")
    public ResponseEntity<RequestInfo> getSessionInfo(Authentication authentication, HttpServletRequest request) {
        // Set username from Spring Security authentication
        requestCounter.setUsername(authentication.getName());
        int count = requestCounter.incrementCountAndGet();
        logger.info("user {} visited this endpoint {} times", requestCounter.getUsername(), count);
        logger.info("Session ID : {}", request.getSession().getId());
        logger.info("Controller - RequestCounterBean ID : {}", requestCounter.getId());
        return ResponseEntity.ok(new RequestInfo(requestCounter.getUsername(), count));
    }

    private class RequestInfo {
        private String username;
        private int requestCount;

        public RequestInfo(String username, int requestCount) {
            this.username = username;
            this.requestCount = requestCount;
        }

        public String getUsername() {
            return username;
        }

        public int getRequestCount() {
            return requestCount;
        }

    }
}