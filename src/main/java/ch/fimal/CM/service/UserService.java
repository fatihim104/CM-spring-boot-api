package ch.fimal.CM.service;

import ch.fimal.CM.model.User;

public interface UserService {
    User getUser(Long id);

    User getUser(String username);

    User saveUser(User user);

    void addRoleToUser(Long userId, Long roleId);
}
