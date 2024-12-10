package org.caicedo.andres.withdrawal.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.caicedo.andres.withdrawal.controller.model.RetrieveWithdrawalsResponse;
import org.caicedo.andres.withdrawal.usecase.RetrieveWithdrawalsUseCase;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("api/v1/withdrawals")
@RequiredArgsConstructor
public class RetrieveWithdrawalsController {

  private final RetrieveWithdrawalsUseCase retrieveWithdrawalsUseCase;

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<RetrieveWithdrawalsResponse> retrieveWithdrawals() {
    return ResponseEntity.ok(retrieveWithdrawalsUseCase.execute());
  }
}
