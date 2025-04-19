package com.learn.session_scope.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.learn.session_scope.controller.RequestCounterController;
import com.learn.session_scope.model.RequestCounter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@Component
public class RateLimitFilter extends OncePerRequestFilter {

    private final Logger logger = LoggerFactory.getLogger(RateLimitFilter.class);

    @Autowired
    private RequestCounter requestCounter;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException,
            IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Only count requests for authenticated users (not anonymous)
        if (authentication != null && authentication.isAuthenticated() && !authentication.getPrincipal().equals("anonymousUser")) {
            System.out.println("==================================================================" +
                    "===============================================================================" +
                    "=========================================");
            logger.info("Filter - RequestCounterBean ID : {}", requestCounter.getId());
            if (requestCounter.getRequestCount() + 1 > 10) {
                logger.info("Request is throttled for user : {}", requestCounter.getUsername() );
                sendRateLimitExceededResponse(request, response);
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    private void sendRateLimitExceededResponse(HttpServletRequest request,
                                               HttpServletResponse response)
            throws IOException {

        response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");

        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("status", HttpStatus.TOO_MANY_REQUESTS.value());
        errorResponse.put("error", "Too Many Requests");
        errorResponse.put("message", "Maximum requests per session exceeded");

        // Add rate limit information
        errorResponse.put("rateLimit", 10);

        try (PrintWriter writer = response.getWriter()) {
            writer.write(new ObjectMapper().writeValueAsString(errorResponse));
            writer.flush();
        }
    }
}
