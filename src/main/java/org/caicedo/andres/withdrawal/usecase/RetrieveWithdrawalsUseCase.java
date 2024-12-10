package org.caicedo.andres.withdrawal.usecase;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.caicedo.andres.withdrawal.controller.model.RetrieveWithdrawalsResponse;
import org.caicedo.andres.withdrawal.controller.model.ScheduledWithdrawal;
import org.caicedo.andres.withdrawal.controller.model.Withdrawal;
import org.caicedo.andres.withdrawal.repository.gateway.WithdrawalRepositoriesGateway;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RetrieveWithdrawalsUseCase {

  private final WithdrawalRepositoriesGateway withdrawalRepositoriesGateway;

  public RetrieveWithdrawalsResponse execute() {

    log.info("Searching withdrawals");

    List<Withdrawal> withdrawals = withdrawalRepositoriesGateway.findAll();

    List<ScheduledWithdrawal> scheduledWithdrawals =
        withdrawalRepositoriesGateway.findAllScheduled();

    log.info(
        "A total of {} withdrawals and {} scheduled withdrawals were found",
        withdrawals.size(),
        scheduledWithdrawals.size());

    return RetrieveWithdrawalsResponse.builder()
        .withdrawals(withdrawals)
        .scheduledWithdrawals(scheduledWithdrawals)
        .build();
  }
}
