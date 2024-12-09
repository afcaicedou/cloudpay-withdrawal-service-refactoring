package unit.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Collections;
import org.caicedo.andres.user.controller.GetUserController;
import org.caicedo.andres.user.controller.model.GetUserResponse;
import org.caicedo.andres.user.controller.model.User;
import org.caicedo.andres.user.usecase.GetUserUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class GetUserControllerTest {

  @InjectMocks private GetUserController getUserController;

  @Mock private GetUserUseCase getUserUseCase;

  private User user;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);

    user =
        User.builder()
            .id(1L)
            .firstName("John Doe")
            .paymentMethods(Collections.emptyList())
            .maxWithdrawalAmount(BigDecimal.valueOf(1000))
            .build();
  }

  @Test
  void testFindUser_Success() {
    GetUserResponse response = GetUserResponse.builder().user(user).build();

    when(getUserUseCase.execute(1L)).thenReturn(response);

    ResponseEntity<GetUserResponse> result = getUserController.findUser(1L);

    assertEquals(HttpStatus.OK, result.getStatusCode());
    assertEquals(user, result.getBody().getUser());
  }
}
