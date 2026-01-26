package ch.fimal.CM.service;

import ch.fimal.CM.model.Permission;
import ch.fimal.CM.model.Role;

public interface RoleService {
  Role saveRole(Role role);

  Permission savePermission(Permission permission);

  void addPermissionToRole(Long roleId, Long permissionId);
}
