package ch.fimal.CM.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.fimal.CM.dto.PermissionRequest;
import ch.fimal.CM.dto.RoleRequest;
import ch.fimal.CM.model.Permission;
import ch.fimal.CM.model.Role;
import ch.fimal.CM.service.RoleService;
import ch.fimal.CM.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/admin")
@AllArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class RoleController {

  private RoleService roleService;
  private UserService userService;

  @PostMapping("/role")
  public ResponseEntity<Role> createRole(@Valid @RequestBody RoleRequest roleRequest) {
    Role role = new Role(roleRequest.name());
    return new ResponseEntity<>(roleService.saveRole(role), HttpStatus.CREATED);
  }

  @PostMapping("/permission")
  public ResponseEntity<Permission> createPermission(@Valid @RequestBody PermissionRequest permissionRequest) {
    Permission permission = new Permission(permissionRequest.name());
    return new ResponseEntity<>(roleService.savePermission(permission), HttpStatus.CREATED);
  }

  @PostMapping("/role/{roleId}/permission/{permissionId}")
  public ResponseEntity<HttpStatus> addPermissionToRole(@PathVariable Long roleId, @PathVariable Long permissionId) {
    roleService.addPermissionToRole(roleId, permissionId);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @PostMapping("/user/{userId}/role/{roleId}")
  public ResponseEntity<HttpStatus> addRoleToUser(@PathVariable Long userId, @PathVariable Long roleId) {
    userService.addRoleToUser(userId, roleId);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
