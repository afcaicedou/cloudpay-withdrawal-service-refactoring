package org.caicedo.andres.user.repository;

import org.caicedo.andres.user.repository.model.PaymentMethodEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentMethodRepository extends JpaRepository<PaymentMethodEntity, Long> {}
