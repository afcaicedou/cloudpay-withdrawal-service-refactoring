package unit.usecases;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.List;
import org.caicedo.andres.user.controller.model.GetAllUsersResponse;
import org.caicedo.andres.user.controller.model.User;
import org.caicedo.andres.user.repository.gateway.UserRepositoryGateway;
import org.caicedo.andres.user.usecase.GetAllUsersUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GetAllUsersUseCaseTest {

  @InjectMocks private GetAllUsersUseCase getAllUsersUseCase;

  @Mock private UserRepositoryGateway userRepositoryGateway;

  private User user1;
  private User user2;

  @BeforeEach
  void setUp() {
    user1 =
        User.builder()
            .id(1L)
            .firstName("John")
            .maxWithdrawalAmount(BigDecimal.valueOf(1000))
            .build();

    user2 =
        User.builder()
            .id(2L)
            .firstName("Jane")
            .maxWithdrawalAmount(BigDecimal.valueOf(1500))
            .build();
  }

  @Test
  void testExecute_usersFound() {
    List<User> users = List.of(user1, user2);
    when(userRepositoryGateway.getUsers()).thenReturn(users);

    GetAllUsersResponse response = getAllUsersUseCase.execute();

    assertNotNull(response);
    assertEquals(2, response.getUsers().size());
    assertEquals("John", response.getUsers().get(0).getFirstName());
    assertEquals("Jane", response.getUsers().get(1).getFirstName());
    verify(userRepositoryGateway, times(1)).getUsers();
  }

  @Test
  void testExecute_noUsersFound() {
    when(userRepositoryGateway.getUsers()).thenReturn(List.of());

    GetAllUsersResponse response = getAllUsersUseCase.execute();

    assertNotNull(response);
    assertTrue(response.getUsers().isEmpty());
    verify(userRepositoryGateway, times(1)).getUsers();
  }
}
