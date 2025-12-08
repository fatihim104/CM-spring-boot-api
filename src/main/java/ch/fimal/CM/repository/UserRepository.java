package ch.fimal.CM.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ch.fimal.CM.model.User;

public interface UserRepository extends JpaRepository<User, Long>{
    User findByUsername(String username);
}
