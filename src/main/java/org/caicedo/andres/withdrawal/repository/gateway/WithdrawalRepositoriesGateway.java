package org.caicedo.andres.withdrawal.repository.gateway;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.caicedo.andres.withdrawal.controller.mapper.ScheduledWithdrawalMapper;
import org.caicedo.andres.withdrawal.controller.mapper.WithdrawalMapper;
import org.caicedo.andres.withdrawal.controller.model.ScheduledWithdrawal;
import org.caicedo.andres.withdrawal.controller.model.Withdrawal;
import org.caicedo.andres.withdrawal.repository.ScheduledWithdrawalRepository;
import org.caicedo.andres.withdrawal.repository.WithdrawalRepository;
import org.caicedo.andres.withdrawal.repository.model.ScheduledWithdrawalEntity;
import org.caicedo.andres.withdrawal.repository.model.WithdrawalEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class WithdrawalRepositoriesGateway {

  private final WithdrawalRepository withdrawalRepository;
  private final ScheduledWithdrawalRepository scheduledWithdrawalRepository;
  private final WithdrawalMapper withdrawalMapper;
  private final ScheduledWithdrawalMapper scheduledWithdrawalMapper;

  public List<Withdrawal> findAll() {

    List<WithdrawalEntity> withdrawals = withdrawalRepository.findAll();

    return withdrawalMapper.toDomain(withdrawals);
  }

  public Optional<WithdrawalEntity> findById(Long id) {

    return withdrawalRepository.findById(id);
  }

  public List<ScheduledWithdrawal> findAllScheduled() {

    List<ScheduledWithdrawalEntity> scheduledWithdrawals = scheduledWithdrawalRepository.findAll();

    return scheduledWithdrawalMapper.toDomain(scheduledWithdrawals);
  }

  public List<ScheduledWithdrawal> findAllByExecuteAtBefore(LocalDateTime currentTime) {

    List<ScheduledWithdrawalEntity> scheduledWithdrawals =
        scheduledWithdrawalRepository.findAllByExecuteAtBefore(currentTime);

    return scheduledWithdrawalMapper.toDomain(scheduledWithdrawals);
  }

  @Transactional
  public WithdrawalEntity save(WithdrawalEntity withdrawal) {

    return withdrawalRepository.save(withdrawal);
  }

  @Transactional
  public void save(ScheduledWithdrawalEntity scheduledWithdrawal) {

    scheduledWithdrawalRepository.save(scheduledWithdrawal);
  }
}
