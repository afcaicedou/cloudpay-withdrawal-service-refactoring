package unit.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.Optional;
import org.caicedo.andres.common.exceptions.PaymentMethodNotFoundException;
import org.caicedo.andres.services.ValidateWithdrawalService;
import org.caicedo.andres.user.repository.gateway.PaymentMethodRepositoryGateway;
import org.caicedo.andres.user.repository.gateway.UserRepositoryGateway;
import org.caicedo.andres.user.repository.model.PaymentMethodEntity;
import org.caicedo.andres.withdrawal.controller.model.PerformWithdrawalRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ValidateWithdrawalServiceTest {

  @Mock private UserRepositoryGateway userRepositoryGateway;

  @Mock private PaymentMethodRepositoryGateway paymentMethodRepositoryGateway;

  @InjectMocks private ValidateWithdrawalService validateWithdrawalService;

  @Test
  void testValidateRequest_PaymentMethodNotFound() {
    PerformWithdrawalRequest request =
        PerformWithdrawalRequest.builder()
            .userId(1L)
            .paymentMethodId(999L)
            .amount(BigDecimal.valueOf(100.0))
            .executeAt("ASAP")
            .build();

    when(paymentMethodRepositoryGateway.findById(request.getPaymentMethodId()))
        .thenReturn(Optional.empty());

    PaymentMethodNotFoundException exception =
        assertThrows(
            PaymentMethodNotFoundException.class,
            () -> validateWithdrawalService.validateRequest(request));

    assertEquals("Payment method not found", exception.getMessage());
    verify(paymentMethodRepositoryGateway, times(1)).findById(request.getPaymentMethodId());
  }

  @Test
  void testValidateRequest_PaymentMethodFound() {
    PerformWithdrawalRequest request =
        PerformWithdrawalRequest.builder()
            .userId(1L)
            .paymentMethodId(999L)
            .amount(BigDecimal.valueOf(100.0))
            .executeAt("ASAP")
            .build();

    PaymentMethodEntity paymentMethodEntity =
        PaymentMethodEntity.builder().id(999L).name("credit_card").build();

    when(paymentMethodRepositoryGateway.findById(request.getPaymentMethodId()))
        .thenReturn(Optional.of(paymentMethodEntity));

    assertDoesNotThrow(() -> validateWithdrawalService.validateRequest(request));
    verify(paymentMethodRepositoryGateway, times(1)).findById(request.getPaymentMethodId());
  }
}
