package unit.gateways;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.caicedo.andres.common.exceptions.UserNotFoundException;
import org.caicedo.andres.user.controller.mapper.UserMapper;
import org.caicedo.andres.user.controller.model.User;
import org.caicedo.andres.user.repository.UserRepository;
import org.caicedo.andres.user.repository.gateway.UserRepositoryGateway;
import org.caicedo.andres.user.repository.model.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserRepositoryGatewayTest {

  @InjectMocks private UserRepositoryGateway userRepositoryGateway;

  @Mock private UserRepository userRepository;

  @Mock private UserMapper userMapper;

  private UserEntity userEntity;
  private User user;

  @BeforeEach
  void setUp() {
    userEntity =
        UserEntity.builder()
            .id(1L)
            .firstName("John")
            .paymentMethods(null)
            .maxWithdrawalAmount(BigDecimal.valueOf(1000))
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();

    user =
        User.builder()
            .id(1L)
            .firstName("John")
            .maxWithdrawalAmount(BigDecimal.valueOf(1000))
            .build();
  }

  @Test
  void testFindById_userFound() {
    when(userRepository.findById(1L)).thenReturn(Optional.of(userEntity));
    when(userMapper.toDomain(userEntity)).thenReturn(user);

    User result = userRepositoryGateway.findById(1L);

    assertNotNull(result);
    assertEquals(user.getId(), result.getId());
    assertEquals(user.getFirstName(), result.getFirstName());
    verify(userRepository, times(1)).findById(1L);
  }

  @Test
  void testFindById_userNotFound() {
    when(userRepository.findById(1L)).thenReturn(Optional.empty());

    UserNotFoundException exception =
        assertThrows(UserNotFoundException.class, () -> userRepositoryGateway.findById(1L));

    assertEquals("User does not exist", exception.getMessage());
    verify(userRepository, times(1)).findById(1L);
  }

  @Test
  void testGetUsers_usersFound() {
    UserEntity userEntity2 =
        UserEntity.builder()
            .id(2L)
            .firstName("Jane")
            .paymentMethods(null)
            .maxWithdrawalAmount(BigDecimal.valueOf(2000))
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();

    List<UserEntity> userEntities = List.of(userEntity, userEntity2);

    when(userRepository.findAll()).thenReturn(userEntities);
    when(userMapper.toDomain(userEntities)).thenReturn(List.of(user, user));

    List<User> result = userRepositoryGateway.getUsers();

    assertNotNull(result);
    assertEquals(2, result.size());
    verify(userRepository, times(1)).findAll();
  }

  @Test
  void testGetUsers_noUsersFound() {
    when(userRepository.findAll()).thenReturn(List.of());

    UserNotFoundException exception =
        assertThrows(UserNotFoundException.class, () -> userRepositoryGateway.getUsers());

    assertEquals("There are no users", exception.getMessage());
    verify(userRepository, times(1)).findAll();
  }
}
