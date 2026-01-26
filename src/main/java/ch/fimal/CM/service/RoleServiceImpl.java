package ch.fimal.CM.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import ch.fimal.CM.exception.EntityNotFoundException;
import ch.fimal.CM.model.Permission;
import ch.fimal.CM.model.Role;
import ch.fimal.CM.repository.PermissionRepository;
import ch.fimal.CM.repository.RoleRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {

  private RoleRepository roleRepository;
  private PermissionRepository permissionRepository;

  @Override
  public Role saveRole(Role role) {
    return roleRepository.save(role);
  }

  @Override
  public Permission savePermission(Permission permission) {
    return permissionRepository.save(permission);
  }

  @Override
  public void addPermissionToRole(Long roleId, Long permissionId) {
    Optional<Role> role = roleRepository.findById(roleId);
    Optional<Permission> permission = permissionRepository.findById(permissionId);

    if (role.isPresent() && permission.isPresent()) {
      Role r = role.get();
      r.getPermissions().add(permission.get());
      roleRepository.save(r);
    } else {
      if (role.isEmpty())
        throw new EntityNotFoundException(roleId, Role.class);
      if (permission.isEmpty())
        throw new EntityNotFoundException(permissionId, Permission.class);
    }
  }

}
