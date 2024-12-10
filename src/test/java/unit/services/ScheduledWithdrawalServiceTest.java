package unit.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.caicedo.andres.common.enums.WithdrawalStatusEnum;
import org.caicedo.andres.common.exceptions.TransactionException;
import org.caicedo.andres.services.EventsService;
import org.caicedo.andres.services.WithdrawalProcessingService;
import org.caicedo.andres.user.controller.mapper.PaymentMethodMapper;
import org.caicedo.andres.user.repository.gateway.PaymentMethodRepositoryGateway;
import org.caicedo.andres.user.repository.model.PaymentMethodEntity;
import org.caicedo.andres.withdrawal.controller.mapper.ScheduledWithdrawalMapper;
import org.caicedo.andres.withdrawal.controller.model.ScheduledWithdrawal;
import org.caicedo.andres.withdrawal.repository.gateway.WithdrawalRepositoriesGateway;
import org.caicedo.andres.withdrawal.repository.model.ScheduledWithdrawalEntity;
import org.caicedo.andres.withdrawal.service.ScheduledWithdrawalService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ScheduledWithdrawalServiceTest {

  @Mock private WithdrawalRepositoriesGateway withdrawalRepositoriesGateway;

  @Mock private PaymentMethodRepositoryGateway paymentMethodRepositoryGateway;

  @Mock private WithdrawalProcessingService withdrawalProcessingService;

  @Mock private EventsService eventsService;

  @Mock private PaymentMethodMapper paymentMethodMapper;

  @Mock private ScheduledWithdrawalMapper scheduledWithdrawalMapper;

  @InjectMocks private ScheduledWithdrawalService scheduledWithdrawalService;

  @Test
  void testRun_WhenPaymentMethodNotFound_ShouldUpdateStatusToFailed() {
    ScheduledWithdrawal scheduledWithdrawal =
        ScheduledWithdrawal.builder()
            .id(1L)
            .paymentMethodId(999L)
            .amount(BigDecimal.valueOf(100.0))
            .executeAt(LocalDateTime.now().minusMinutes(1))
            .status(WithdrawalStatusEnum.PENDING)
            .build();

    when(withdrawalRepositoriesGateway.findAllByExecuteAtBefore(any()))
        .thenReturn(List.of(scheduledWithdrawal));

    when(paymentMethodRepositoryGateway.findById(scheduledWithdrawal.getPaymentMethodId()))
        .thenReturn(Optional.empty());

    scheduledWithdrawalService.run();

    verify(withdrawalRepositoriesGateway, times(1)).save((ScheduledWithdrawalEntity) any());

    assertEquals(WithdrawalStatusEnum.FAILED, scheduledWithdrawal.getStatus());
  }

  @Test
  void testRun_WhenTransactionFails_ShouldUpdateStatusToFailed() throws TransactionException {
    ScheduledWithdrawal scheduledWithdrawal =
        ScheduledWithdrawal.builder()
            .id(1L)
            .paymentMethodId(999L)
            .amount(BigDecimal.valueOf(100.0))
            .executeAt(LocalDateTime.now().minusMinutes(1))
            .status(WithdrawalStatusEnum.PENDING)
            .build();

    PaymentMethodEntity paymentMethodEntity =
        PaymentMethodEntity.builder().id(999L).name("credit_card").build();

    when(withdrawalRepositoriesGateway.findAllByExecuteAtBefore(any()))
        .thenReturn(List.of(scheduledWithdrawal));

    when(paymentMethodRepositoryGateway.findById(scheduledWithdrawal.getPaymentMethodId()))
        .thenReturn(Optional.of(paymentMethodEntity));

    when(withdrawalProcessingService.sendToProcessing(any(), any()))
        .thenThrow(new TransactionException("Transaction failed"));

    scheduledWithdrawalService.run();

    verify(withdrawalRepositoriesGateway, times(1)).save((ScheduledWithdrawalEntity) any());

    assertEquals(WithdrawalStatusEnum.FAILED, scheduledWithdrawal.getStatus());
  }

  @Test
  void testRun_WhenSuccessfulTransaction_ShouldUpdateStatusToProcessing()
      throws TransactionException {
    ScheduledWithdrawal scheduledWithdrawal =
        ScheduledWithdrawal.builder()
            .id(1L)
            .paymentMethodId(999L)
            .amount(BigDecimal.valueOf(100.0))
            .executeAt(LocalDateTime.now().minusMinutes(1))
            .status(WithdrawalStatusEnum.PENDING)
            .build();

    PaymentMethodEntity paymentMethodEntity =
        PaymentMethodEntity.builder().id(999L).name("credit_card").build();

    when(withdrawalRepositoriesGateway.findAllByExecuteAtBefore(any()))
        .thenReturn(List.of(scheduledWithdrawal));

    when(paymentMethodRepositoryGateway.findById(scheduledWithdrawal.getPaymentMethodId()))
        .thenReturn(Optional.of(paymentMethodEntity));

    when(withdrawalProcessingService.sendToProcessing(any(), any())).thenReturn(123L);

    scheduledWithdrawalService.run();

    verify(withdrawalRepositoriesGateway, times(1)).save((ScheduledWithdrawalEntity) any());

    assertEquals(WithdrawalStatusEnum.PROCESSING, scheduledWithdrawal.getStatus());
  }

  @Test
  void testRun_WhenUnknownExceptionOccurs_ShouldUpdateStatusToInternalError() {
    ScheduledWithdrawal scheduledWithdrawal =
        ScheduledWithdrawal.builder()
            .id(1L)
            .paymentMethodId(999L)
            .amount(BigDecimal.valueOf(100.0))
            .executeAt(LocalDateTime.now().minusMinutes(1))
            .status(WithdrawalStatusEnum.PENDING)
            .build();

    PaymentMethodEntity paymentMethodEntity =
        PaymentMethodEntity.builder().id(999L).name("credit_card").build();

    when(withdrawalRepositoriesGateway.findAllByExecuteAtBefore(any()))
        .thenReturn(List.of(scheduledWithdrawal));

    when(paymentMethodRepositoryGateway.findById(scheduledWithdrawal.getPaymentMethodId()))
        .thenReturn(Optional.of(paymentMethodEntity));

    when(withdrawalProcessingService.sendToProcessing(any(), any()))
        .thenThrow(new RuntimeException("Unknown error"));

    scheduledWithdrawalService.run();

    verify(withdrawalRepositoriesGateway, times(1)).save((ScheduledWithdrawalEntity) any());
    assertEquals(WithdrawalStatusEnum.INTERNAL_ERROR, scheduledWithdrawal.getStatus());
  }
}
