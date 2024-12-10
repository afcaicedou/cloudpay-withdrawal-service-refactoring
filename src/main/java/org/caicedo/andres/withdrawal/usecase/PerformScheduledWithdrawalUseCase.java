package org.caicedo.andres.withdrawal.usecase;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.caicedo.andres.common.enums.WithdrawalStatusEnum;
import org.caicedo.andres.services.ValidateWithdrawalService;
import org.caicedo.andres.withdrawal.controller.mapper.ScheduledWithdrawalMapper;
import org.caicedo.andres.withdrawal.controller.model.PerformWithdrawalRequest;
import org.caicedo.andres.withdrawal.controller.model.PerformWithdrawalResponse;
import org.caicedo.andres.withdrawal.controller.model.ScheduledWithdrawal;
import org.caicedo.andres.withdrawal.repository.gateway.WithdrawalRepositoriesGateway;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PerformScheduledWithdrawalUseCase implements WithdrawalUseCase {

  private final ValidateWithdrawalService validateWithdrawalService;
  private final WithdrawalRepositoriesGateway withdrawalRepositoriesGateway;
  private final ScheduledWithdrawalMapper scheduledWithdrawalMapper;

  @Override
  public PerformWithdrawalResponse execute(PerformWithdrawalRequest performWithdrawalRequest) {

    validateWithdrawalService.validateRequest(performWithdrawalRequest);

    ScheduledWithdrawal scheduledWithdrawal =
        ScheduledWithdrawal.builder()
            .userId(performWithdrawalRequest.getUserId())
            .paymentMethodId(performWithdrawalRequest.getPaymentMethodId())
            .amount(performWithdrawalRequest.getAmount())
            .status(WithdrawalStatusEnum.PENDING)
            .executeAt(LocalDateTime.parse(performWithdrawalRequest.getExecuteAt()))
            .build();

    withdrawalRepositoriesGateway.save(scheduledWithdrawalMapper.toEntity(scheduledWithdrawal));

    return PerformWithdrawalResponse.builder().scheduledWithdrawal(scheduledWithdrawal).build();
  }
}
