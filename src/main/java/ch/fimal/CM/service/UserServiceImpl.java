package ch.fimal.CM.service;

import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import ch.fimal.CM.exception.EntityNotFoundException;
import ch.fimal.CM.model.Role;
import ch.fimal.CM.model.User;
import ch.fimal.CM.repository.UserRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private ch.fimal.CM.repository.RoleRepository roleRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public User getUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        return unwrapUser(user, id);
    }

    @Override
    public User getUser(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return unwrapUser(user, 404L);
    }

    @Override
    public User saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        if (user.getRoles().isEmpty()) {
            Role role = roleRepository.findByName("USER").get();
            // Warning: This assumes 'USER' role exists. DataSeeder must run first.
            // Better to handle Optional, but for now assuming it exists.
            user.getRoles().add(role);
        }
        return userRepository.save(user);
    }

    @Override
    public void addRoleToUser(Long userId, Long roleId) {
        Optional<User> user = userRepository.findById(userId);
        Optional<Role> role = roleRepository.findById(roleId);

        if (user.isPresent() && role.isPresent()) {
            User u = user.get();
            u.getRoles().add(role.get());
            userRepository.save(u);
        } else {
            if (user.isEmpty())
                throw new EntityNotFoundException(userId, User.class);
            if (role.isEmpty())
                throw new EntityNotFoundException(roleId, Role.class);
        }
    }

    static User unwrapUser(Optional<User> entity, Long id) {
        if (entity.isPresent())
            return entity.get();
        else
            throw new EntityNotFoundException(id, User.class);
    }
}
