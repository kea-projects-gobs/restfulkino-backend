package dk.security.service;

import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

// Service for managing the state of tokens (valid or invalid)
@Service
public class TokenStore {
    private final ConcurrentHashMap<String, Boolean> tokenStore = new ConcurrentHashMap<>();

    public void storeToken(String token, boolean isValid) {
        tokenStore.put(token, isValid);
    }

    public boolean isValid(String token) {
        return tokenStore.getOrDefault(token, false);
    }
    
    public void invalidateToken(String token) {
        tokenStore.put(token, false);
    }
}
