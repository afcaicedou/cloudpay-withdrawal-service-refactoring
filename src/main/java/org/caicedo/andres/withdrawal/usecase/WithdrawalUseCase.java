package org.caicedo.andres.withdrawal.usecase;

import org.caicedo.andres.withdrawal.controller.model.PerformWithdrawalRequest;
import org.caicedo.andres.withdrawal.controller.model.PerformWithdrawalResponse;

public interface WithdrawalUseCase {

  PerformWithdrawalResponse execute(PerformWithdrawalRequest performWithdrawalRequest);
}
