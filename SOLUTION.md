Regarding the bonus questions:

We noticed that in the current solution, we are losing some outgoing events related to withdrawals. We MUST 100% notify
listeners regarding any withdrawal statuses. For example: in WithdrawalService.processScheduledmethod, we updated the
status to processing in the database (line 89), and then we sent an event (line 90). What if the event failed to send (
e.q. connection issues to a messaging provider)?

My proposal:

To ensure 100% reliability in notifying listeners about withdrawal statuses, my solution includes a mechanism to make
sure that the delivery of events is always done.

- Transactional Outbox Pattern:

A transactional outbox table to record the events that need to be sent.
Ensuring that writing to the outbox table and updating the withdrawal status in the database occur within the same
database transaction.

This guarantees consistency: if the withdrawal status is updated, the event is logged for delivery.

- Event Publisher:

A background process (or separate service) continuously reads events from the outbox table and attempts to deliver
them to the messaging provider.
If an event fails to send (e.g., due to connection issues), it retries sending the event, potentially with exponential
backoff.

- Retries and Dead Letter Queue (DLQ):

Implementing a retry logic for failed events to handle transient failures.
If retries exceed a configured threshold, move the failed event to a dead-letter queue for manual review or further
investigation.

- Monitoring and Alerting:

Setting up monitoring for the event publisher to detect issues in real time, such as failed events or a growing backlo
in the outbox table.
Alerts can ensure that we are notified quickly about problems.

- Idempotency:

Ensure that event consumption by listeners is idempotent to handle duplicate deliveries, which may occur if a retry
succeeds after an initial failure.
This approach ensures that no event is lost, even in the case of a failure in the messaging system, and withdrawal
statuses are always reliably communicated to the listeners.