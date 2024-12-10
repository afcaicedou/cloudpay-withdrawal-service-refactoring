package org.caicedo.andres.withdrawal.repository.model;

import static jakarta.persistence.GenerationType.IDENTITY;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.*;
import org.caicedo.andres.common.enums.WithdrawalStatusEnum;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@Setter
@Builder
@Table(name = "withdrawals")
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class WithdrawalEntity {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  private Long transactionId;
  private Long userId;
  private Long paymentMethodId;

  @Column(precision = 18, scale = 2)
  private BigDecimal amount;

  @CreatedDate
  @Column(updatable = false)
  private LocalDateTime createdAt;

  @LastModifiedDate private LocalDateTime updatedAt;

  @Enumerated(EnumType.STRING)
  private WithdrawalStatusEnum status;

  @PrePersist
  protected void onCreate() {
    createdAt = LocalDateTime.now();
  }

  @PreUpdate
  protected void onUpdate() {
    updatedAt = LocalDateTime.now();
  }
}
