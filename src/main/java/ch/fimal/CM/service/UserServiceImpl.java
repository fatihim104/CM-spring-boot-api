package ch.fimal.CM.service;


import java.util.Optional;

import org.springframework.stereotype.Service;

import ch.fimal.CM.exception.EntityNotFoundException;
import ch.fimal.CM.model.User;
import ch.fimal.CM.repository.UserRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Override
    public User getUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        return unwrapUser(user, id);
    }

    @Override
    public User saveUser(User user) {
     return userRepository.save(user);
    }
    
    static User unwrapUser(Optional<User> entity, Long id) {
        if (entity.isPresent()) return entity.get();
        else throw new EntityNotFoundException(id, User.class);
    }
}
