package unit.factory;

import static org.junit.jupiter.api.Assertions.assertSame;

import org.caicedo.andres.withdrawal.factory.WithdrawalUseCaseFactory;
import org.caicedo.andres.withdrawal.usecase.PerformScheduledWithdrawalUseCase;
import org.caicedo.andres.withdrawal.usecase.PerformWithdrawalUseCase;
import org.caicedo.andres.withdrawal.usecase.WithdrawalUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class WithdrawalUseCaseFactoryTest {

  @Mock private PerformWithdrawalUseCase performWithdrawalUseCase;

  @Mock private PerformScheduledWithdrawalUseCase performScheduledWithdrawalUseCase;

  @InjectMocks private WithdrawalUseCaseFactory withdrawalUseCaseFactory;

  @Test
  void testGetUseCase_WhenExecuteAtIsASAP_ShouldReturnPerformWithdrawalUseCase() {
    WithdrawalUseCase useCase = withdrawalUseCaseFactory.getUseCase("ASAP");

    assertSame(performWithdrawalUseCase, useCase);
  }

  @Test
  void testGetUseCase_WhenExecuteAtIsNotASAP_ShouldReturnPerformScheduledWithdrawalUseCase() {
    WithdrawalUseCase useCase = withdrawalUseCaseFactory.getUseCase("Scheduled");

    assertSame(performScheduledWithdrawalUseCase, useCase);
  }
}
