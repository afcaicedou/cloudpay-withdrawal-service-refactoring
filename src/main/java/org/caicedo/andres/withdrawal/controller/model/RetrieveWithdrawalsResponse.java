package org.caicedo.andres.withdrawal.controller.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RetrieveWithdrawalsResponse {

  private List<Withdrawal> withdrawals;
  private List<ScheduledWithdrawal> scheduledWithdrawals;
}
