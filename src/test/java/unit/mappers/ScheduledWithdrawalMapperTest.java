package unit.mappers;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import org.caicedo.andres.common.enums.WithdrawalStatusEnum;
import org.caicedo.andres.withdrawal.controller.mapper.ScheduledWithdrawalMapper;
import org.caicedo.andres.withdrawal.controller.model.ScheduledWithdrawal;
import org.caicedo.andres.withdrawal.repository.model.ScheduledWithdrawalEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ScheduledWithdrawalMapperTest {

  private ScheduledWithdrawalMapper scheduledWithdrawalMapper;

  private ScheduledWithdrawalEntity scheduledWithdrawalEntity;
  private ScheduledWithdrawal scheduledWithdrawal;

  @BeforeEach
  void setUp() {
    scheduledWithdrawalMapper = Mappers.getMapper(ScheduledWithdrawalMapper.class);

    scheduledWithdrawalEntity =
        ScheduledWithdrawalEntity.builder()
            .id(1L)
            .transactionId(12345L)
            .userId(6789L)
            .paymentMethodId(9876L)
            .amount(BigDecimal.valueOf(500.00))
            .executeAt(LocalDateTime.now().plusDays(1))
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .status(WithdrawalStatusEnum.PENDING)
            .build();

    scheduledWithdrawal =
        ScheduledWithdrawal.builder()
            .id(1L)
            .transactionId(12345L)
            .userId(6789L)
            .paymentMethodId(9876L)
            .amount(BigDecimal.valueOf(500.00))
            .executeAt(LocalDateTime.now().plusDays(1))
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .status(WithdrawalStatusEnum.PENDING)
            .build();
  }

  @Test
  void testToDomain() {
    ScheduledWithdrawal result = scheduledWithdrawalMapper.toDomain(scheduledWithdrawalEntity);

    assertNotNull(result);
    assertEquals(scheduledWithdrawalEntity.getId(), result.getId());
    assertEquals(scheduledWithdrawalEntity.getTransactionId(), result.getTransactionId());
    assertEquals(scheduledWithdrawalEntity.getUserId(), result.getUserId());
    assertEquals(scheduledWithdrawalEntity.getPaymentMethodId(), result.getPaymentMethodId());
    assertEquals(scheduledWithdrawalEntity.getAmount(), result.getAmount());
    assertEquals(scheduledWithdrawalEntity.getExecuteAt(), result.getExecuteAt());
    assertEquals(scheduledWithdrawalEntity.getStatus(), result.getStatus());
  }

  @Test
  void testToEntity() {
    ScheduledWithdrawalEntity result = scheduledWithdrawalMapper.toEntity(scheduledWithdrawal);

    assertNotNull(result);
    assertEquals(scheduledWithdrawal.getId(), result.getId());
    assertEquals(scheduledWithdrawal.getTransactionId(), result.getTransactionId());
    assertEquals(scheduledWithdrawal.getUserId(), result.getUserId());
    assertEquals(scheduledWithdrawal.getPaymentMethodId(), result.getPaymentMethodId());
    assertEquals(scheduledWithdrawal.getAmount(), result.getAmount());
    assertEquals(scheduledWithdrawal.getExecuteAt(), result.getExecuteAt());
    assertEquals(scheduledWithdrawal.getStatus(), result.getStatus());
  }

  @Test
  void testToDomainList() {
    ScheduledWithdrawalEntity entity1 = scheduledWithdrawalEntity;
    ScheduledWithdrawalEntity entity2 =
        ScheduledWithdrawalEntity.builder().id(2L).transactionId(12346L).build();

    List<ScheduledWithdrawalEntity> entityList = List.of(entity1, entity2);
    List<ScheduledWithdrawal> result = scheduledWithdrawalMapper.toDomain(entityList);

    assertNotNull(result);
    assertEquals(2, result.size());
    assertEquals(entity1.getId(), result.get(0).getId());
    assertEquals(entity2.getId(), result.get(1).getId());
  }
}
