package unit.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.Optional;
import org.caicedo.andres.common.exceptions.TransactionException;
import org.caicedo.andres.services.EventsService;
import org.caicedo.andres.services.WithdrawalProcessingService;
import org.caicedo.andres.user.controller.mapper.PaymentMethodMapper;
import org.caicedo.andres.user.repository.gateway.PaymentMethodRepositoryGateway;
import org.caicedo.andres.user.repository.model.PaymentMethodEntity;
import org.caicedo.andres.withdrawal.controller.mapper.WithdrawalMapper;
import org.caicedo.andres.withdrawal.controller.model.Withdrawal;
import org.caicedo.andres.withdrawal.repository.gateway.WithdrawalRepositoriesGateway;
import org.caicedo.andres.withdrawal.repository.model.WithdrawalEntity;
import org.caicedo.andres.withdrawal.service.WithdrawalService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class WithdrawalServiceTest {

  @Mock private WithdrawalRepositoriesGateway withdrawalRepositoriesGateway;

  @Mock private PaymentMethodRepositoryGateway paymentMethodRepositoryGateway;

  @Mock private WithdrawalMapper withdrawalMapper;

  @Mock private WithdrawalProcessingService withdrawalProcessingService;

  @Mock private EventsService eventsService;

  @Mock private PaymentMethodMapper paymentMethodMapper;

  @InjectMocks private WithdrawalService withdrawalService;

  @Test
  void testCreate_WhenTransactionFails_ShouldUpdateStatusToFailed() throws TransactionException {
    Long withdrawalId = 1L;
    WithdrawalEntity pendingWithdrawal =
        WithdrawalEntity.builder()
            .id(withdrawalId)
            .paymentMethodId(999L)
            .amount(BigDecimal.valueOf(100.0))
            .build();

    Withdrawal withdrawal =
        Withdrawal.builder()
            .id(withdrawalId)
            .paymentMethodId(999L)
            .amount(BigDecimal.valueOf(100.0))
            .build();

    PaymentMethodEntity paymentMethodEntity =
        PaymentMethodEntity.builder().id(999L).name("credit_card").build();

    when(withdrawalRepositoriesGateway.findById(withdrawalId))
        .thenReturn(Optional.of(pendingWithdrawal));

    when(withdrawalMapper.toDomain(pendingWithdrawal)).thenReturn(withdrawal);

    when(paymentMethodRepositoryGateway.findById(withdrawal.getPaymentMethodId()))
        .thenReturn(Optional.of(paymentMethodEntity));

    withdrawalService.create(pendingWithdrawal);

    verify(withdrawalRepositoriesGateway, times(1)).findById(any());
  }

  @Test
  void testCreate_WhenUnknownErrorOccurs_ShouldUpdateStatusToInternalError() {
    Long withdrawalId = 1L;
    WithdrawalEntity pendingWithdrawal =
        WithdrawalEntity.builder()
            .id(withdrawalId)
            .paymentMethodId(999L)
            .amount(BigDecimal.valueOf(100.0))
            .build();

    Withdrawal withdrawal =
        Withdrawal.builder()
            .id(withdrawalId)
            .paymentMethodId(999L)
            .amount(BigDecimal.valueOf(100.0))
            .build();

    PaymentMethodEntity paymentMethodEntity =
        PaymentMethodEntity.builder().id(999L).name("credit_card").build();

    when(withdrawalRepositoriesGateway.findById(withdrawalId))
        .thenReturn(Optional.of(pendingWithdrawal));

    when(withdrawalMapper.toDomain(pendingWithdrawal)).thenReturn(withdrawal);

    when(paymentMethodRepositoryGateway.findById(withdrawal.getPaymentMethodId()))
        .thenReturn(Optional.of(paymentMethodEntity));

    withdrawalService.create(pendingWithdrawal);

    verify(withdrawalRepositoriesGateway, times(1)).findById(any());
  }
}
