package ch.fimal.CM.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ch.fimal.CM.model.RefreshToken;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long>{
     Optional<RefreshToken> findByToken(String token);
}
