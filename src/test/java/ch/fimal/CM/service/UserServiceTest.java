package ch.fimal.CM.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import ch.fimal.CM.exception.EntityNotFoundException;
import ch.fimal.CM.model.Role;
import ch.fimal.CM.model.User;
import ch.fimal.CM.repository.RoleRepository;
import ch.fimal.CM.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

  @Mock
  private UserRepository userRepository;

  @Mock
  private RoleRepository roleRepository;

  @Mock
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  @InjectMocks
  private UserServiceImpl userService;

  @Test
  void getUser_shouldReturnUser_whenUserExists() {
    // User constructor: firstName, lastName, email, password
    User user = new User("Test", "User", "test@test.com", "password");
    user.setId(1L);
    when(userRepository.findById(1L)).thenReturn(Optional.of(user));

    User foundUser = userService.getUser(1L);

    assertNotNull(foundUser);
    assertEquals("test@test.com", foundUser.getEmail());
  }

  @Test
  void getUser_shouldThrowException_whenUserDoesNotExist() {
    when(userRepository.findById(1L)).thenReturn(Optional.empty());

    assertThrows(EntityNotFoundException.class, () -> {
      userService.getUser(1L);
    });
  }

  @Test
  void saveUser_shouldEncodePasswordAndAssignDefaultRole() {
    User user = new User("New", "User", "new@test.com", "rawPassword");
    Role userRole = new Role("USER");

    when(bCryptPasswordEncoder.encode("rawPassword")).thenReturn("encodedPassword");
    when(roleRepository.findByName("USER")).thenReturn(Optional.of(userRole));
    when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

    User savedUser = userService.saveUser(user);

    assertEquals("encodedPassword", savedUser.getPassword());
    assertEquals(1, savedUser.getRoles().size());
    assertEquals("USER", savedUser.getRoles().iterator().next().getName());

    verify(userRepository, times(1)).save(user);
  }
}
