package ch.fimal.CM.repository;

import org.springframework.data.repository.CrudRepository;
import ch.fimal.CM.model.Role;

public interface RoleRepository extends CrudRepository<Role, Long> {
  java.util.Optional<Role> findByName(String name);
}
