package unit.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Collections;
import org.caicedo.andres.user.controller.GetAllUsersController;
import org.caicedo.andres.user.controller.model.GetAllUsersResponse;
import org.caicedo.andres.user.usecase.GetAllUsersUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class GetAllUsersControllerTest {

  @InjectMocks private GetAllUsersController getAllUsersController;

  @Mock private GetAllUsersUseCase getAllUsersUseCase;

  private GetAllUsersResponse response;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);

    response = GetAllUsersResponse.builder().users(Collections.emptyList()).build();
  }

  @Test
  void testFindAllUsers_Success() {
    when(getAllUsersUseCase.execute()).thenReturn(response);

    ResponseEntity<GetAllUsersResponse> result = getAllUsersController.findAllUsers();

    assertEquals(HttpStatus.OK, result.getStatusCode());
    assertEquals(response, result.getBody());
  }

  @Test
  void testFindAllUsers_EmptyList() {
    GetAllUsersResponse emptyResponse =
        GetAllUsersResponse.builder().users(Collections.emptyList()).build();

    when(getAllUsersUseCase.execute()).thenReturn(emptyResponse);

    ResponseEntity<GetAllUsersResponse> result = getAllUsersController.findAllUsers();

    assertEquals(HttpStatus.OK, result.getStatusCode());
    assertEquals(emptyResponse, result.getBody());
  }
}
