package ch.fimal.CM.repository;

import org.springframework.data.repository.CrudRepository;
import ch.fimal.CM.model.Permission;

public interface PermissionRepository extends CrudRepository<Permission, Long> {

}
