
package com.learn.bean.scopes.filter;

import com.learn.bean.scopes.model.UserContext;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Date;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {
    private final Logger logger = LoggerFactory.getLogger(JwtTokenFilter.class);

    @Autowired
    private UserContext userContext;

    @Value("${secret-key}")
    private String secretKey;

    public JwtTokenFilter() {
        logger.info("JwtTokenFilter bean is created");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        try {
            String header = request.getHeader("Authorization");
            String token = (header != null && header.startsWith("Bearer ")) ? header.substring(7) : null;
            if (token != null) {
                Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
                validateToken(claims);
                userContext.setUserID(claims.getSubject());
                userContext.setName((String) claims.get("name"));
                logger.info("userContext object is populated by JWtTokenFilter");
                logger.info("User context object in jwt token filter {}",userContext);
            }
            chain.doFilter(request, response);
        } catch (JwtException e) {
            logger.warn("JWT validation failed: {}", e.getMessage());
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
        }
    }

    private void validateToken(Claims claims) throws JwtException {
        if (claims.getExpiration().before(new Date())) {
            throw new JwtException("Token expired");
        }
        // userID validation code goes here
        // extract the userID and check the userID in DB, if userID is present,
        // it's a valid request, otherwise thrown Invalid user exception
    }

}
