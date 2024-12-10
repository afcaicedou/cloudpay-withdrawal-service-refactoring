package org.caicedo.andres.withdrawal.factory;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.caicedo.andres.withdrawal.usecase.PerformScheduledWithdrawalUseCase;
import org.caicedo.andres.withdrawal.usecase.PerformWithdrawalUseCase;
import org.caicedo.andres.withdrawal.usecase.WithdrawalUseCase;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class WithdrawalUseCaseFactory {

  private final PerformWithdrawalUseCase performWithdrawalUseCase;
  private final PerformScheduledWithdrawalUseCase performScheduledWithdrawalUseCase;

  public WithdrawalUseCase getUseCase(String executeAt) {
    if ("ASAP".equals(executeAt)) {
      return performWithdrawalUseCase;
    } else {
      return performScheduledWithdrawalUseCase;
    }
  }
}
