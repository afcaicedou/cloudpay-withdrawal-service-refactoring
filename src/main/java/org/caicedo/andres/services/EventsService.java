package org.caicedo.andres.services;

import java.security.SecureRandom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.caicedo.andres.withdrawal.controller.model.ScheduledWithdrawal;
import org.caicedo.andres.withdrawal.controller.model.Withdrawal;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventsService {

  /*
   * Normally, an event object should be created in where the withdrawal object is included as well as headers, etc
   */

  public Long send(Withdrawal withdrawal) {
    // Here the withdrawal event should be sent to an event queue and its id is returned

    log.info("Withdrawal event :: {} sent", withdrawal);

    SecureRandom secureRandom = new SecureRandom();
    return secureRandom.nextLong();
  }

  public Long send(ScheduledWithdrawal scheduledWithdrawal) {
    // Here the scheduledWithdrawal event should be sent to an event queue and its id is returned

    log.info("Scheduled withdrawal event :: {} sent", scheduledWithdrawal);

    SecureRandom secureRandom = new SecureRandom();
    return secureRandom.nextLong();
  }
}
