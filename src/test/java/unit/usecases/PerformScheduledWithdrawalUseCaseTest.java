package unit.usecases;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.caicedo.andres.common.enums.WithdrawalStatusEnum;
import org.caicedo.andres.services.ValidateWithdrawalService;
import org.caicedo.andres.withdrawal.controller.mapper.ScheduledWithdrawalMapper;
import org.caicedo.andres.withdrawal.controller.model.PerformWithdrawalRequest;
import org.caicedo.andres.withdrawal.controller.model.PerformWithdrawalResponse;
import org.caicedo.andres.withdrawal.controller.model.ScheduledWithdrawal;
import org.caicedo.andres.withdrawal.repository.gateway.WithdrawalRepositoriesGateway;
import org.caicedo.andres.withdrawal.repository.model.ScheduledWithdrawalEntity;
import org.caicedo.andres.withdrawal.usecase.PerformScheduledWithdrawalUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PerformScheduledWithdrawalUseCaseTest {

  @Mock private ValidateWithdrawalService validateWithdrawalService;

  @Mock private WithdrawalRepositoriesGateway withdrawalRepositoriesGateway;

  @Mock private ScheduledWithdrawalMapper scheduledWithdrawalMapper;

  @InjectMocks private PerformScheduledWithdrawalUseCase performScheduledWithdrawalUseCase;

  @Test
  void testExecute() {
    PerformWithdrawalRequest request =
        PerformWithdrawalRequest.builder()
            .userId(1L)
            .paymentMethodId(100L)
            .amount(BigDecimal.valueOf(200))
            .executeAt("2024-12-09T10:15:30")
            .build();

    ScheduledWithdrawal scheduledWithdrawal =
        ScheduledWithdrawal.builder()
            .userId(1L)
            .paymentMethodId(100L)
            .amount(BigDecimal.valueOf(200))
            .status(WithdrawalStatusEnum.PENDING)
            .executeAt(LocalDateTime.parse("2024-12-09T10:15:30"))
            .build();

    when(scheduledWithdrawalMapper.toEntity(any()))
        .thenReturn(ScheduledWithdrawalEntity.builder().build());

    PerformWithdrawalResponse response = performScheduledWithdrawalUseCase.execute(request);

    assertNotNull(response);
    assertEquals(scheduledWithdrawal.getUserId(), response.getScheduledWithdrawal().getUserId());
    assertEquals(
        scheduledWithdrawal.getPaymentMethodId(),
        response.getScheduledWithdrawal().getPaymentMethodId());
    assertEquals(scheduledWithdrawal.getAmount(), response.getScheduledWithdrawal().getAmount());
    assertEquals(
        scheduledWithdrawal.getExecuteAt(), response.getScheduledWithdrawal().getExecuteAt());
    assertEquals(scheduledWithdrawal.getStatus(), response.getScheduledWithdrawal().getStatus());

    verify(withdrawalRepositoriesGateway, times(1)).save((ScheduledWithdrawalEntity) any());
  }
}
