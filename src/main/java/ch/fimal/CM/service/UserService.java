package ch.fimal.CM.service;

import ch.fimal.CM.model.User;

public interface UserService {
    User getUser(Long id);
    User saveUser(User user);    
}
