package ch.fimal.CM.service;

import ch.fimal.CM.model.TokenResponse;

public interface AuthService {
    TokenResponse refresh(String refreshTokenValue);
    void logout(String refreshTokenValue);
}
