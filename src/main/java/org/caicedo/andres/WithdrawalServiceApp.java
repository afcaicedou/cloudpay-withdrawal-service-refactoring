package org.caicedo.andres;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = {"org.caicedo.andres"})
@EnableJpaAuditing
@EnableScheduling
public class WithdrawalServiceApp {
  public static void main(String[] args) {
    SpringApplication.run(WithdrawalServiceApp.class, args);
  }
}
