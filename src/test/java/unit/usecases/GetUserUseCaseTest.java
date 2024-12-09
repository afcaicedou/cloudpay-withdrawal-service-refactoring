package unit.usecases;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import org.caicedo.andres.common.exceptions.UserNotFoundException;
import org.caicedo.andres.user.controller.model.GetUserResponse;
import org.caicedo.andres.user.controller.model.User;
import org.caicedo.andres.user.repository.gateway.UserRepositoryGateway;
import org.caicedo.andres.user.usecase.GetUserUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GetUserUseCaseTest {

  @InjectMocks private GetUserUseCase getUserUseCase;

  @Mock private UserRepositoryGateway userRepositoryGateway;

  private User user;

  @BeforeEach
  void setUp() {
    user =
        User.builder()
            .id(1L)
            .firstName("John")
            .maxWithdrawalAmount(BigDecimal.valueOf(1000))
            .build();
  }

  @Test
  void testExecute_userFound() {
    Long userId = 1L;
    when(userRepositoryGateway.findById(userId)).thenReturn(user);

    GetUserResponse response = getUserUseCase.execute(userId);

    assertNotNull(response);
    assertEquals(1L, response.getUser().getId());
    assertEquals("John", response.getUser().getFirstName());
    verify(userRepositoryGateway, times(1)).findById(userId);
  }

  @Test
  void testExecute_userNotFound() {
    Long userId = 2L;
    when(userRepositoryGateway.findById(userId))
        .thenThrow(new UserNotFoundException("User does not exist"));

    UserNotFoundException exception =
        assertThrows(UserNotFoundException.class, () -> getUserUseCase.execute(userId));
    assertEquals("User does not exist", exception.getMessage());
    verify(userRepositoryGateway, times(1)).findById(userId);
  }
}
