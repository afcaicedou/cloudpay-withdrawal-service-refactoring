package org.caicedo.andres.withdrawal.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.caicedo.andres.common.enums.WithdrawalStatusEnum;
import org.caicedo.andres.common.exceptions.TransactionException;
import org.caicedo.andres.services.EventsService;
import org.caicedo.andres.services.WithdrawalProcessingService;
import org.caicedo.andres.user.controller.mapper.PaymentMethodMapper;
import org.caicedo.andres.user.repository.gateway.PaymentMethodRepositoryGateway;
import org.caicedo.andres.user.repository.model.PaymentMethodEntity;
import org.caicedo.andres.withdrawal.controller.mapper.ScheduledWithdrawalMapper;
import org.caicedo.andres.withdrawal.controller.model.ScheduledWithdrawal;
import org.caicedo.andres.withdrawal.repository.gateway.WithdrawalRepositoriesGateway;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScheduledWithdrawalService {

  private final WithdrawalRepositoriesGateway withdrawalRepositoriesGateway;
  private final PaymentMethodRepositoryGateway paymentMethodRepositoryGateway;
  private final WithdrawalProcessingService withdrawalProcessingService;
  private final EventsService eventsService;
  private final PaymentMethodMapper paymentMethodMapper;
  private final ScheduledWithdrawalMapper scheduledWithdrawalMapper;

  @Scheduled(fixedDelay = 5000)
  @Transactional
  public void run() {

    List<ScheduledWithdrawal> scheduledWithdrawals =
        withdrawalRepositoriesGateway.findAllByExecuteAtBefore(LocalDateTime.now());

    scheduledWithdrawals.stream()
        .filter(ScheduledWithdrawalService::isInValidStatus)
        .forEach(this::processScheduled);
  }

  private static boolean isInValidStatus(ScheduledWithdrawal scheduledWithdrawal) {
    return scheduledWithdrawal.getStatus() == WithdrawalStatusEnum.PENDING
        || scheduledWithdrawal.getStatus() == WithdrawalStatusEnum.INTERNAL_ERROR;
  }

  private void processScheduled(ScheduledWithdrawal scheduledWithdrawal) {

    Optional<PaymentMethodEntity> paymentMethod =
        paymentMethodRepositoryGateway.findById(scheduledWithdrawal.getPaymentMethodId());

    if (paymentMethod.isEmpty()) {
      log.error(
          "Payment method with id {} not found for scheduled withdrawal id {}",
          scheduledWithdrawal.getPaymentMethodId(),
          scheduledWithdrawal.getId());

      updateScheduledWithdrawalStatus(scheduledWithdrawal, WithdrawalStatusEnum.FAILED);

      return;
    }

    try {
      Long transactionId =
          withdrawalProcessingService.sendToProcessing(
              scheduledWithdrawal.getAmount(), paymentMethodMapper.toDomain(paymentMethod.get()));

      scheduledWithdrawal.setTransactionId(transactionId);

      updateScheduledWithdrawalStatus(scheduledWithdrawal, WithdrawalStatusEnum.PROCESSING);
    } catch (TransactionException e) {
      log.warn(
          "Transaction failed for scheduled withdrawal id {}: {}",
          scheduledWithdrawal.getId(),
          e.getMessage());

      updateScheduledWithdrawalStatus(scheduledWithdrawal, WithdrawalStatusEnum.FAILED);
    } catch (Exception e) {
      log.error(
          "Internal error processing scheduled withdrawal id {}: {}",
          scheduledWithdrawal.getId(),
          e.getMessage());

      updateScheduledWithdrawalStatus(scheduledWithdrawal, WithdrawalStatusEnum.INTERNAL_ERROR);
    }
  }

  private void updateScheduledWithdrawalStatus(
      ScheduledWithdrawal scheduledWithdrawal, WithdrawalStatusEnum status) {

    scheduledWithdrawal.setStatus(status);
    withdrawalRepositoriesGateway.save(scheduledWithdrawalMapper.toEntity(scheduledWithdrawal));

    Long eventId = eventsService.send(scheduledWithdrawal);

    log.info("Event with id {} sent", eventId);
  }
}
