package org.caicedo.andres.withdrawal.service;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.caicedo.andres.common.enums.WithdrawalStatusEnum;
import org.caicedo.andres.common.exceptions.TransactionException;
import org.caicedo.andres.services.EventsService;
import org.caicedo.andres.services.WithdrawalProcessingService;
import org.caicedo.andres.user.controller.mapper.PaymentMethodMapper;
import org.caicedo.andres.user.repository.gateway.PaymentMethodRepositoryGateway;
import org.caicedo.andres.user.repository.model.PaymentMethodEntity;
import org.caicedo.andres.withdrawal.controller.mapper.WithdrawalMapper;
import org.caicedo.andres.withdrawal.controller.model.Withdrawal;
import org.caicedo.andres.withdrawal.repository.gateway.WithdrawalRepositoriesGateway;
import org.caicedo.andres.withdrawal.repository.model.WithdrawalEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class WithdrawalService {

  private final WithdrawalRepositoriesGateway withdrawalRepositoriesGateway;
  private final PaymentMethodRepositoryGateway paymentMethodRepositoryGateway;
  private final WithdrawalMapper withdrawalMapper;
  private final WithdrawalProcessingService withdrawalProcessingService;
  private final EventsService eventsService;
  private final PaymentMethodMapper paymentMethodMapper;

  @Transactional
  public void create(WithdrawalEntity pendingWithdrawal) {

    CompletableFuture.runAsync(() -> processWithdrawal(pendingWithdrawal.getId()))
        .exceptionally(
            ex -> {
              log.error("Error processing withdrawal with id: {}", pendingWithdrawal.getId(), ex);
              return null;
            });
  }

  private void processWithdrawal(Long withdrawalId) {

    Optional<WithdrawalEntity> withdrawalEntity =
        withdrawalRepositoriesGateway.findById(withdrawalId);

    if (withdrawalEntity.isEmpty()) {
      log.error("Withdrawal with id {} was not found.", withdrawalId);
      return;
    }

    Withdrawal withdrawal = withdrawalMapper.toDomain(withdrawalEntity.get());

    Optional<PaymentMethodEntity> paymentMethodEntity =
        paymentMethodRepositoryGateway.findById(withdrawal.getPaymentMethodId());

    if (paymentMethodEntity.isEmpty()) {
      log.error("Payment method with id {} was not found.", withdrawal.getPaymentMethodId());

      updateWithdrawalStatus(withdrawal, WithdrawalStatusEnum.FAILED);

      return;
    }

    try {
      Long transactionId =
          withdrawalProcessingService.sendToProcessing(
              withdrawal.getAmount(), paymentMethodMapper.toDomain(paymentMethodEntity.get()));

      withdrawal.setTransactionId(transactionId);

      updateWithdrawalStatus(withdrawal, WithdrawalStatusEnum.PROCESSING);
    } catch (TransactionException e) {
      log.warn("Transaction failed for withdrawal id {}: {}", withdrawalId, e.getMessage());
      updateWithdrawalStatus(withdrawal, WithdrawalStatusEnum.FAILED);
    } catch (Exception e) {
      log.error("Internal error processing withdrawal id {}: {}", withdrawalId, e.getMessage());
      updateWithdrawalStatus(withdrawal, WithdrawalStatusEnum.INTERNAL_ERROR);
    }
  }

  private void updateWithdrawalStatus(Withdrawal withdrawal, WithdrawalStatusEnum status) {

    withdrawal.setStatus(status);
    withdrawalRepositoriesGateway.save(withdrawalMapper.toEntity(withdrawal));

    Long eventId = eventsService.send(withdrawal);

    log.info("Event with id {} sent", eventId);
  }
}
