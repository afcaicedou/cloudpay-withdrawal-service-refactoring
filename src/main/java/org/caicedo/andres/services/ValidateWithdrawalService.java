package org.caicedo.andres.services;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.caicedo.andres.common.exceptions.PaymentMethodNotFoundException;
import org.caicedo.andres.user.repository.gateway.PaymentMethodRepositoryGateway;
import org.caicedo.andres.user.repository.gateway.UserRepositoryGateway;
import org.caicedo.andres.user.repository.model.PaymentMethodEntity;
import org.caicedo.andres.withdrawal.controller.model.PerformWithdrawalRequest;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ValidateWithdrawalService {

  private final UserRepositoryGateway userRepositoryGateway;
  private final PaymentMethodRepositoryGateway paymentMethodRepositoryGateway;

  public void validateRequest(PerformWithdrawalRequest performWithdrawalRequest) {

    userRepositoryGateway.findById(performWithdrawalRequest.getUserId());

    Optional<PaymentMethodEntity> paymentMethod =
        paymentMethodRepositoryGateway.findById(performWithdrawalRequest.getPaymentMethodId());

    if (paymentMethod.isEmpty()) {
      log.info(
          "Payment method with id {} not found", performWithdrawalRequest.getPaymentMethodId());

      throw new PaymentMethodNotFoundException("Payment method not found");
    }
  }
}
