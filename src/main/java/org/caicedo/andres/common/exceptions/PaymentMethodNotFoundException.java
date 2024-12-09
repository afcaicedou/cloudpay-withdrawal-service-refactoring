package org.caicedo.andres.common.exceptions;

public class PaymentMethodNotFoundException extends RuntimeException {

  public PaymentMethodNotFoundException(String message) {
    super(message);
  }
}
