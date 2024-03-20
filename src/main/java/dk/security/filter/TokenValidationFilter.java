package dk.security.filter;

import dk.security.service.TokenStore;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class TokenValidationFilter extends OncePerRequestFilter {

    private final TokenStore tokenStore;

    public TokenValidationFilter(TokenStore tokenStore) {
        this.tokenStore = tokenStore;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String requestURI = request.getRequestURI();
    
        // Check if the request is targeting the logout endpoint
        if ("/api/auth/logout".equals(requestURI)) {
            // If it is, directly proceed to the next filter without validating the token
            filterChain.doFilter(request, response);
            return; // Skip the rest of the token validation logic
        }
        // Rest of the token validation logic
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7); // Extracts the token from the Authorization header
            boolean isValid = tokenStore.isValid(token); // Checks if the extracted token is valid
            System.out.println("Token: " + token + " isValid: " + isValid); // Logging for debugging
            if (!isValid) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid Token");
                return;
            }
        }
        filterChain.doFilter(request, response); // Proceeds with the filter chain if the token is valid
    }
}

