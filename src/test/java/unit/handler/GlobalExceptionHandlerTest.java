package unit.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.lang.reflect.Method;
import org.caicedo.andres.common.exceptions.PaymentMethodNotFoundException;
import org.caicedo.andres.common.exceptions.UserNotFoundException;
import org.caicedo.andres.common.handler.GlobalExceptionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class GlobalExceptionHandlerTest {

  @InjectMocks private GlobalExceptionHandler globalExceptionHandler;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void handleUserNotFoundException() throws Exception {
    UserNotFoundException exception = new UserNotFoundException("User not found");

    Method method =
        GlobalExceptionHandler.class.getDeclaredMethod(
            "handleUserNotFoundException", UserNotFoundException.class);
    method.setAccessible(true);

    ResponseEntity<String> responseEntity =
        (ResponseEntity<String>) method.invoke(globalExceptionHandler, exception);

    assertNotNull(responseEntity);
    assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    assertEquals("User not found", responseEntity.getBody());
  }

  @Test
  void handlePaymentMethodNotFoundException() throws Exception {
    PaymentMethodNotFoundException exception =
        new PaymentMethodNotFoundException("Payment method not found");

    Method method =
        GlobalExceptionHandler.class.getDeclaredMethod(
            "handlePaymentMethodNotFoundExceptionException", PaymentMethodNotFoundException.class);
    method.setAccessible(true);

    ResponseEntity<String> responseEntity =
        (ResponseEntity<String>) method.invoke(globalExceptionHandler, exception);

    assertNotNull(responseEntity);
    assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    assertEquals("Payment method not found", responseEntity.getBody());
  }

  @Test
  void handleRuntimeException() throws Exception {
    RuntimeException exception = new RuntimeException("Internal server error");

    Method method =
        GlobalExceptionHandler.class.getDeclaredMethod(
            "handleRuntimeException", RuntimeException.class);
    method.setAccessible(true);

    ResponseEntity<String> responseEntity =
        (ResponseEntity<String>) method.invoke(globalExceptionHandler, exception);

    assertNotNull(responseEntity);
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    assertEquals("Internal server error", responseEntity.getBody());
  }
}
