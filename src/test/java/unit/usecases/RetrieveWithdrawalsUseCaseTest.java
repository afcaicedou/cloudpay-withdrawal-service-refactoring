package unit.usecases;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import org.caicedo.andres.withdrawal.controller.model.RetrieveWithdrawalsResponse;
import org.caicedo.andres.withdrawal.controller.model.ScheduledWithdrawal;
import org.caicedo.andres.withdrawal.controller.model.Withdrawal;
import org.caicedo.andres.withdrawal.repository.gateway.WithdrawalRepositoriesGateway;
import org.caicedo.andres.withdrawal.usecase.RetrieveWithdrawalsUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RetrieveWithdrawalsUseCaseTest {

  @Mock private WithdrawalRepositoriesGateway withdrawalRepositoriesGateway;

  @InjectMocks private RetrieveWithdrawalsUseCase retrieveWithdrawalsUseCase;

  @Test
  void testExecute() {
    List<Withdrawal> withdrawals =
        List.of(
            Withdrawal.builder()
                .userId(1L)
                .paymentMethodId(100L)
                .amount(BigDecimal.valueOf(200))
                .build(),
            Withdrawal.builder()
                .userId(2L)
                .paymentMethodId(101L)
                .amount(BigDecimal.valueOf(300))
                .build());

    List<ScheduledWithdrawal> scheduledWithdrawals =
        List.of(
            ScheduledWithdrawal.builder()
                .userId(1L)
                .paymentMethodId(100L)
                .amount(BigDecimal.valueOf(150))
                .executeAt(LocalDateTime.parse("2024-12-09T10:15:30"))
                .build(),
            ScheduledWithdrawal.builder()
                .userId(2L)
                .paymentMethodId(101L)
                .amount(BigDecimal.valueOf(250))
                .executeAt(LocalDateTime.parse("2024-12-09T10:45:30"))
                .build());

    when(withdrawalRepositoriesGateway.findAll()).thenReturn(withdrawals);
    when(withdrawalRepositoriesGateway.findAllScheduled()).thenReturn(scheduledWithdrawals);

    RetrieveWithdrawalsResponse response = retrieveWithdrawalsUseCase.execute();

    assertNotNull(response);
    assertEquals(2, response.getWithdrawals().size());
    assertEquals(2, response.getScheduledWithdrawals().size());
    verify(withdrawalRepositoriesGateway, times(1)).findAll();
    verify(withdrawalRepositoriesGateway, times(1)).findAllScheduled();
  }
}
