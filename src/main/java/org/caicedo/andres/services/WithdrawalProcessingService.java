package org.caicedo.andres.services;

import java.math.BigDecimal;
import java.security.SecureRandom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.caicedo.andres.user.controller.model.PaymentMethod;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class WithdrawalProcessingService {

  public Long sendToProcessing(BigDecimal amount, PaymentMethod paymentMethod) {

    // Here we would choose a service (different implementations of it) for each payment method
    log.info(
        "Processing payment to provider with amount {} and payment method {}",
        amount,
        paymentMethod);

    SecureRandom secureRandom = new SecureRandom();
    return secureRandom.nextLong();
  }
}
