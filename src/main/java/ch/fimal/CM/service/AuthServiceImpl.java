package ch.fimal.CM.service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import ch.fimal.CM.model.RefreshToken;
import ch.fimal.CM.model.TokenResponse;
import ch.fimal.CM.model.User;
import ch.fimal.CM.repository.RefreshTokenRepository;
import ch.fimal.CM.security.SecurityConstants;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    @Transactional
    public TokenResponse refresh(String refreshTokenValue) {
        RefreshToken token = refreshTokenRepository
                .findByToken(refreshTokenValue)
                .orElseThrow(() -> new RuntimeException("Invalid refresh token"));

        if (token.isRevoked() || token.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Refresh token expired or revoked");
        }

        token.setRevoked(true);

        RefreshToken newRefreshToken = createNewRefreshToken(token.getUser());
        refreshTokenRepository.save(newRefreshToken);

        String accessToken = generateAccessToken(token.getUser());

        return new TokenResponse(accessToken, newRefreshToken.getToken());
    }

    @Override
    public void logout(String refreshTokenValue) {
        refreshTokenRepository.findByToken(refreshTokenValue)
                .ifPresent(token -> {
                    token.setRevoked(true);
                    refreshTokenRepository.save(token);
                });
    }

    public String generateAccessToken(User user) {
        var authorities = user.getRoles().stream()
                .map(role -> "ROLE_" + role.getName())
                .collect(Collectors.toList());

        // Add permissions if needed, or keep it simple with roles first.
        // If you rely on hasAuthority('course:create'), you need permissions here too.
        user.getRoles().forEach(role -> {
            role.getPermissions().forEach(permission -> authorities.add(permission.getName()));
        });

        return JWT.create()
                .withSubject(user.getEmail())
                .withClaim("authorities", authorities)
                .withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstants.ACCESS_TOKEN_EXPIRATION))
                .sign(Algorithm.HMAC512(SecurityConstants.SECRET_KEY));
    }

    public RefreshToken createNewRefreshToken(User user) {
        String newRefreshTokenValue = UUID.randomUUID().toString();
        RefreshToken newRefreshToken = new RefreshToken();
        newRefreshToken.setToken(newRefreshTokenValue);
        newRefreshToken.setUser(user);
        newRefreshToken.setExpiresAt(LocalDateTime.now().plusDays(7));
        return newRefreshToken;
    }

}
