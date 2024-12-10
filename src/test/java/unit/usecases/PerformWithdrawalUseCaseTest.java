package unit.usecases;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import org.caicedo.andres.common.enums.WithdrawalStatusEnum;
import org.caicedo.andres.services.ValidateWithdrawalService;
import org.caicedo.andres.withdrawal.controller.mapper.WithdrawalMapper;
import org.caicedo.andres.withdrawal.controller.model.PerformWithdrawalRequest;
import org.caicedo.andres.withdrawal.controller.model.PerformWithdrawalResponse;
import org.caicedo.andres.withdrawal.controller.model.Withdrawal;
import org.caicedo.andres.withdrawal.repository.gateway.WithdrawalRepositoriesGateway;
import org.caicedo.andres.withdrawal.repository.model.WithdrawalEntity;
import org.caicedo.andres.withdrawal.service.WithdrawalService;
import org.caicedo.andres.withdrawal.usecase.PerformWithdrawalUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PerformWithdrawalUseCaseTest {

  @Mock private ValidateWithdrawalService validateWithdrawalService;

  @Mock private WithdrawalService withdrawalService;

  @Mock private WithdrawalRepositoriesGateway withdrawalRepositoriesGateway;

  @Mock private WithdrawalMapper withdrawalMapper;

  @InjectMocks private PerformWithdrawalUseCase performWithdrawalUseCase;

  @Test
  void testExecute() {
    PerformWithdrawalRequest request =
        PerformWithdrawalRequest.builder()
            .userId(1L)
            .paymentMethodId(100L)
            .amount(BigDecimal.valueOf(200))
            .executeAt("ASAP")
            .build();

    Withdrawal withdrawal =
        Withdrawal.builder()
            .userId(1L)
            .paymentMethodId(100L)
            .amount(BigDecimal.valueOf(200))
            .status(WithdrawalStatusEnum.PENDING)
            .build();

    WithdrawalEntity withdrawalEntity =
        WithdrawalEntity.builder()
            .id(1L)
            .paymentMethodId(100L)
            .amount(BigDecimal.valueOf(200))
            .userId(1L)
            .status(WithdrawalStatusEnum.PENDING)
            .build();

    when(withdrawalMapper.toDomain((WithdrawalEntity) any())).thenReturn(withdrawal);

    PerformWithdrawalResponse response = performWithdrawalUseCase.execute(request);

    assertNotNull(response);
    assertNotNull(response.getWithdrawal());
    assertEquals(WithdrawalStatusEnum.PENDING, response.getWithdrawal().getStatus());
    verify(withdrawalService, times(1)).create(any());
  }
}
