package ch.fimal.CM.controller;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import ch.fimal.CM.model.RefreshToken;
import ch.fimal.CM.repository.RefreshTokenRepository;
import ch.fimal.CM.security.SecurityConstants;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class RefreshTokenController {

        private final RefreshTokenRepository refreshTokenRepository;

        @PostMapping("/refresh")
        public ResponseEntity<Map<String, String>> refreshToken(@RequestBody Map<String, String> request) {

                String refreshTokenValue = request.get("refreshToken");

                RefreshToken refreshToken = refreshTokenRepository
                                .findByToken(refreshTokenValue)
                                .orElseThrow(() -> new RuntimeException("Invalid refresh token"));

                if (refreshToken.isRevoked() || refreshToken.getExpiresAt().isBefore(LocalDateTime.now())) {
                        throw new RuntimeException("Refresh token expired or revoked");
                }

                // ROTATION
                refreshToken.setRevoked(true);
                refreshTokenRepository.save(refreshToken);

                String newRefreshTokenValue = UUID.randomUUID().toString();

                RefreshToken newRefreshToken = new RefreshToken();
                newRefreshToken.setToken(newRefreshTokenValue);
                newRefreshToken.setUser(refreshToken.getUser());
                newRefreshToken.setExpiresAt(LocalDateTime.now().plusDays(7));

                refreshTokenRepository.save(newRefreshToken);

                String username = refreshToken.getUser().getEmail();

                String newAccessToken = JWT.create()
                                .withSubject(username)
                                .withExpiresAt(
                                                new Date(System.currentTimeMillis()
                                                                + SecurityConstants.ACCESS_TOKEN_EXPIRATION))
                                .sign(Algorithm.HMAC512(SecurityConstants.SECRET_KEY));

                return ResponseEntity.ok(
                                Map.of(
                                                "accessToken", newAccessToken,
                                                "refreshToken", newRefreshTokenValue));
        }

        @PostMapping("/logout")
        public void logout(@RequestBody Map<String, String> request) {

                String refreshTokenValue = request.get("refreshToken");

                RefreshToken token = refreshTokenRepository
                                .findByToken(refreshTokenValue)
                                .orElseThrow();

                token.setRevoked(true);
                refreshTokenRepository.save(token);
        }
}
