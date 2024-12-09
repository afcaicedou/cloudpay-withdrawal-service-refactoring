package org.caicedo.andres.user.controller.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PaymentMethod {

  private Long id;
  private String name;
}
