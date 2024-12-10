package org.caicedo.andres.withdrawal.controller.model;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PerformWithdrawalRequest {

  @NotNull private Long userId;

  @NotNull private Long paymentMethodId;

  @NotNull private BigDecimal amount;

  @NotNull private String executeAt;
}
