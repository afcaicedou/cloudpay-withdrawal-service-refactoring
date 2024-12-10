package org.caicedo.andres.withdrawal.repository;

import java.time.LocalDateTime;
import java.util.List;
import org.caicedo.andres.withdrawal.repository.model.ScheduledWithdrawalEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduledWithdrawalRepository
    extends JpaRepository<ScheduledWithdrawalEntity, Long> {

  List<ScheduledWithdrawalEntity> findAllByExecuteAtBefore(LocalDateTime date);
}
