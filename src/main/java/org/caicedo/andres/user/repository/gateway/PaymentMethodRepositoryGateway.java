package org.caicedo.andres.user.repository.gateway;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.caicedo.andres.user.repository.PaymentMethodRepository;
import org.caicedo.andres.user.repository.model.PaymentMethodEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentMethodRepositoryGateway {

  private final PaymentMethodRepository paymentMethodRepository;

  public Optional<PaymentMethodEntity> findById(Long id) {
    return paymentMethodRepository.findById(id);
  }
}
