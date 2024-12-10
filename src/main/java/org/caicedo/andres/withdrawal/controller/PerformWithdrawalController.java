package org.caicedo.andres.withdrawal.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.caicedo.andres.withdrawal.controller.model.PerformWithdrawalRequest;
import org.caicedo.andres.withdrawal.controller.model.PerformWithdrawalResponse;
import org.caicedo.andres.withdrawal.factory.WithdrawalUseCaseFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("api/v1/withdrawals")
@RequiredArgsConstructor
public class PerformWithdrawalController {

  private final WithdrawalUseCaseFactory withdrawalUseCaseFactory;

  @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<PerformWithdrawalResponse> withdrawal(
      @RequestBody PerformWithdrawalRequest performWithdrawalRequest) {
    return ResponseEntity.ok(
        withdrawalUseCaseFactory
            .getUseCase(performWithdrawalRequest.getExecuteAt())
            .execute(performWithdrawalRequest));
  }
}
