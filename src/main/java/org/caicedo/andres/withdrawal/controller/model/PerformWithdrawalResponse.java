package org.caicedo.andres.withdrawal.controller.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PerformWithdrawalResponse {

  private Withdrawal withdrawal;
  private ScheduledWithdrawal scheduledWithdrawal;
}
