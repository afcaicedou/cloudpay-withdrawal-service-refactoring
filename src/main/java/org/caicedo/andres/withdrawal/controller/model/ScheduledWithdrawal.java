package org.caicedo.andres.withdrawal.controller.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.caicedo.andres.common.enums.WithdrawalStatusEnum;

@Getter
@Setter
@Builder
@ToString
public class ScheduledWithdrawal implements Serializable {

  private Long id;
  private Long transactionId;
  private Long userId;
  private Long paymentMethodId;
  private BigDecimal amount;
  private LocalDateTime executeAt;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
  private WithdrawalStatusEnum status;
}
