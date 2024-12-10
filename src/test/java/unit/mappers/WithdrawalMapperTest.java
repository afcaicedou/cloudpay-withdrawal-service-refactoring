package unit.mappers;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import org.caicedo.andres.common.enums.WithdrawalStatusEnum;
import org.caicedo.andres.withdrawal.controller.mapper.WithdrawalMapper;
import org.caicedo.andres.withdrawal.controller.model.Withdrawal;
import org.caicedo.andres.withdrawal.repository.model.WithdrawalEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class WithdrawalMapperTest {

  private WithdrawalMapper withdrawalMapper;

  private WithdrawalEntity withdrawalEntity;
  private Withdrawal withdrawal;

  @BeforeEach
  void setUp() {
    withdrawalMapper = Mappers.getMapper(WithdrawalMapper.class);

    withdrawalEntity =
        WithdrawalEntity.builder()
            .id(1L)
            .transactionId(12345L)
            .userId(6789L)
            .paymentMethodId(9876L)
            .amount(BigDecimal.valueOf(500.00))
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .status(WithdrawalStatusEnum.PENDING)
            .build();

    withdrawal =
        Withdrawal.builder()
            .id(1L)
            .transactionId(12345L)
            .userId(6789L)
            .paymentMethodId(9876L)
            .amount(BigDecimal.valueOf(500.00))
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .status(WithdrawalStatusEnum.PENDING)
            .build();
  }

  @Test
  void testToDomain() {
    Withdrawal result = withdrawalMapper.toDomain(withdrawalEntity);

    assertNotNull(result);
    assertEquals(withdrawalEntity.getId(), result.getId());
    assertEquals(withdrawalEntity.getTransactionId(), result.getTransactionId());
    assertEquals(withdrawalEntity.getUserId(), result.getUserId());
    assertEquals(withdrawalEntity.getPaymentMethodId(), result.getPaymentMethodId());
    assertEquals(withdrawalEntity.getAmount(), result.getAmount());
    assertEquals(withdrawalEntity.getCreatedAt(), result.getCreatedAt());
    assertEquals(withdrawalEntity.getStatus(), result.getStatus());
  }

  @Test
  void testToEntity() {
    WithdrawalEntity result = withdrawalMapper.toEntity(withdrawal);

    assertNotNull(result);
    assertEquals(withdrawal.getId(), result.getId());
    assertEquals(withdrawal.getTransactionId(), result.getTransactionId());
    assertEquals(withdrawal.getUserId(), result.getUserId());
    assertEquals(withdrawal.getPaymentMethodId(), result.getPaymentMethodId());
    assertEquals(withdrawal.getAmount(), result.getAmount());
    assertEquals(withdrawal.getCreatedAt(), result.getCreatedAt());
    assertEquals(withdrawal.getStatus(), result.getStatus());
  }

  @Test
  void testToDomainList() {
    WithdrawalEntity entity1 = withdrawalEntity;
    WithdrawalEntity entity2 = WithdrawalEntity.builder().id(2L).transactionId(12346L).build();

    List<WithdrawalEntity> entityList = List.of(entity1, entity2);
    List<Withdrawal> result = withdrawalMapper.toDomain(entityList);

    assertNotNull(result);
    assertEquals(2, result.size());
    assertEquals(entity1.getId(), result.get(0).getId());
    assertEquals(entity2.getId(), result.get(1).getId());
  }
}
