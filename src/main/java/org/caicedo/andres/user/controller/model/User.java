package org.caicedo.andres.user.controller.model;

import java.math.BigDecimal;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class User {

  private Long id;
  private String firstName;
  private List<PaymentMethod> paymentMethods;
  private BigDecimal maxWithdrawalAmount;
}
