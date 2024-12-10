package org.caicedo.andres.withdrawal.usecase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.caicedo.andres.common.enums.WithdrawalStatusEnum;
import org.caicedo.andres.services.ValidateWithdrawalService;
import org.caicedo.andres.withdrawal.controller.mapper.WithdrawalMapper;
import org.caicedo.andres.withdrawal.controller.model.PerformWithdrawalRequest;
import org.caicedo.andres.withdrawal.controller.model.PerformWithdrawalResponse;
import org.caicedo.andres.withdrawal.controller.model.Withdrawal;
import org.caicedo.andres.withdrawal.repository.gateway.WithdrawalRepositoriesGateway;
import org.caicedo.andres.withdrawal.repository.model.WithdrawalEntity;
import org.caicedo.andres.withdrawal.service.WithdrawalService;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PerformWithdrawalUseCase implements WithdrawalUseCase {

  private final ValidateWithdrawalService validateWithdrawalService;
  private final WithdrawalService withdrawalService;
  private final WithdrawalRepositoriesGateway withdrawalRepositoriesGateway;
  private final WithdrawalMapper withdrawalMapper;

  @Override
  public PerformWithdrawalResponse execute(PerformWithdrawalRequest performWithdrawalRequest) {

    validateWithdrawalService.validateRequest(performWithdrawalRequest);

    Withdrawal withdrawal =
        Withdrawal.builder()
            .userId(performWithdrawalRequest.getUserId())
            .paymentMethodId(performWithdrawalRequest.getPaymentMethodId())
            .amount(performWithdrawalRequest.getAmount())
            .status(WithdrawalStatusEnum.PENDING)
            .build();

    WithdrawalEntity pendingWithdrawal =
        withdrawalRepositoriesGateway.save(withdrawalMapper.toEntity(withdrawal));

    withdrawalService.create(pendingWithdrawal);

    return PerformWithdrawalResponse.builder()
        .withdrawal(withdrawalMapper.toDomain(pendingWithdrawal))
        .build();
  }
}
