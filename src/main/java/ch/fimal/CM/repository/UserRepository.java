package ch.fimal.CM.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import ch.fimal.CM.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
