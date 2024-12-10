package org.caicedo.andres.withdrawal.repository;

import org.caicedo.andres.withdrawal.repository.model.WithdrawalEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WithdrawalRepository extends JpaRepository<WithdrawalEntity, Long> {}
