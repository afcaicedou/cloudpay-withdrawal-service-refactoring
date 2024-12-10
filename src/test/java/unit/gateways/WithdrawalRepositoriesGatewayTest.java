package unit.gateways;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.caicedo.andres.common.enums.WithdrawalStatusEnum;
import org.caicedo.andres.withdrawal.controller.mapper.ScheduledWithdrawalMapper;
import org.caicedo.andres.withdrawal.controller.mapper.WithdrawalMapper;
import org.caicedo.andres.withdrawal.controller.model.ScheduledWithdrawal;
import org.caicedo.andres.withdrawal.controller.model.Withdrawal;
import org.caicedo.andres.withdrawal.repository.ScheduledWithdrawalRepository;
import org.caicedo.andres.withdrawal.repository.WithdrawalRepository;
import org.caicedo.andres.withdrawal.repository.gateway.WithdrawalRepositoriesGateway;
import org.caicedo.andres.withdrawal.repository.model.ScheduledWithdrawalEntity;
import org.caicedo.andres.withdrawal.repository.model.WithdrawalEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class WithdrawalRepositoriesGatewayTest {

  @Mock private WithdrawalRepository withdrawalRepository;

  @Mock private ScheduledWithdrawalRepository scheduledWithdrawalRepository;

  @Mock private WithdrawalMapper withdrawalMapper;

  @Mock private ScheduledWithdrawalMapper scheduledWithdrawalMapper;

  @InjectMocks private WithdrawalRepositoriesGateway withdrawalRepositoriesGateway;

  private WithdrawalEntity withdrawalEntity;
  private ScheduledWithdrawalEntity scheduledWithdrawalEntity;

  @BeforeEach
  void setUp() {
    withdrawalEntity =
        WithdrawalEntity.builder()
            .id(1L)
            .amount(BigDecimal.valueOf(100.00))
            .status(WithdrawalStatusEnum.PENDING)
            .build();

    scheduledWithdrawalEntity =
        ScheduledWithdrawalEntity.builder()
            .id(1L)
            .amount(BigDecimal.valueOf(100.00))
            .executeAt(LocalDateTime.now().plusDays(1))
            .status(WithdrawalStatusEnum.PENDING)
            .build();
  }

  @Test
  void testFindAll() {
    WithdrawalEntity withdrawalEntity1 =
        WithdrawalEntity.builder()
            .id(1L)
            .amount(BigDecimal.valueOf(100.00))
            .status(WithdrawalStatusEnum.PENDING)
            .build();
    WithdrawalEntity withdrawalEntity2 =
        WithdrawalEntity.builder()
            .id(2L)
            .amount(BigDecimal.valueOf(200.00))
            .status(WithdrawalStatusEnum.SUCCESS)
            .build();

    List<WithdrawalEntity> withdrawalEntities = List.of(withdrawalEntity1, withdrawalEntity2);

    when(withdrawalRepository.findAll()).thenReturn(withdrawalEntities);
    when(withdrawalMapper.toDomain(withdrawalEntities))
        .thenReturn(
            List.of(Withdrawal.builder().id(1L).build(), Withdrawal.builder().id(2L).build()));

    List<Withdrawal> result = withdrawalRepositoriesGateway.findAll();

    assertNotNull(result);
    assertEquals(2, result.size());
    verify(withdrawalRepository).findAll();
    verify(withdrawalMapper).toDomain(withdrawalEntities);
  }

  @Test
  void testFindById() {
    Long withdrawalId = 1L;

    when(withdrawalRepository.findById(withdrawalId)).thenReturn(Optional.of(withdrawalEntity));

    Optional<WithdrawalEntity> result = withdrawalRepositoriesGateway.findById(withdrawalId);

    assertTrue(result.isPresent());
    verify(withdrawalRepository).findById(withdrawalId);
  }

  @Test
  void testFindAllScheduled() {
    ScheduledWithdrawalEntity scheduledWithdrawalEntity1 =
        ScheduledWithdrawalEntity.builder()
            .id(1L)
            .amount(BigDecimal.valueOf(100.00))
            .executeAt(LocalDateTime.now().plusDays(1))
            .status(WithdrawalStatusEnum.PENDING)
            .build();
    ScheduledWithdrawalEntity scheduledWithdrawalEntity2 =
        ScheduledWithdrawalEntity.builder()
            .id(2L)
            .amount(BigDecimal.valueOf(200.00))
            .executeAt(LocalDateTime.now().plusDays(2))
            .status(WithdrawalStatusEnum.PENDING)
            .build();

    List<ScheduledWithdrawalEntity> scheduledWithdrawalEntities =
        List.of(scheduledWithdrawalEntity1, scheduledWithdrawalEntity2);

    when(scheduledWithdrawalRepository.findAll()).thenReturn(scheduledWithdrawalEntities);
    when(scheduledWithdrawalMapper.toDomain(scheduledWithdrawalEntities))
        .thenReturn(
            List.of(
                ScheduledWithdrawal.builder().id(1L).build(),
                ScheduledWithdrawal.builder().id(2L).build()));

    List<ScheduledWithdrawal> result = withdrawalRepositoriesGateway.findAllScheduled();

    assertNotNull(result);
    assertEquals(2, result.size());
    verify(scheduledWithdrawalRepository).findAll();
    verify(scheduledWithdrawalMapper).toDomain(scheduledWithdrawalEntities);
  }

  @Test
  void testFindAllByExecuteAtBefore() {
    LocalDateTime currentTime = LocalDateTime.now();
    ScheduledWithdrawalEntity scheduledWithdrawalEntity1 =
        ScheduledWithdrawalEntity.builder()
            .id(1L)
            .amount(BigDecimal.valueOf(100.00))
            .executeAt(currentTime.minusDays(1))
            .status(WithdrawalStatusEnum.PENDING)
            .build();
    ScheduledWithdrawalEntity scheduledWithdrawalEntity2 =
        ScheduledWithdrawalEntity.builder()
            .id(2L)
            .amount(BigDecimal.valueOf(200.00))
            .executeAt(currentTime.minusDays(2))
            .status(WithdrawalStatusEnum.PENDING)
            .build();

    List<ScheduledWithdrawalEntity> scheduledWithdrawalEntities =
        List.of(scheduledWithdrawalEntity1, scheduledWithdrawalEntity2);

    when(scheduledWithdrawalRepository.findAllByExecuteAtBefore(currentTime))
        .thenReturn(scheduledWithdrawalEntities);
    when(scheduledWithdrawalMapper.toDomain(scheduledWithdrawalEntities))
        .thenReturn(
            List.of(
                ScheduledWithdrawal.builder().id(1L).build(),
                ScheduledWithdrawal.builder().id(2L).build()));

    List<ScheduledWithdrawal> result =
        withdrawalRepositoriesGateway.findAllByExecuteAtBefore(currentTime);

    assertNotNull(result);
    assertEquals(2, result.size());
    verify(scheduledWithdrawalRepository).findAllByExecuteAtBefore(currentTime);
    verify(scheduledWithdrawalMapper).toDomain(scheduledWithdrawalEntities);
  }

  @Test
  void testSaveWithdrawal() {
    when(withdrawalRepository.save(withdrawalEntity)).thenReturn(withdrawalEntity);

    WithdrawalEntity result = withdrawalRepositoriesGateway.save(withdrawalEntity);

    assertNotNull(result);
    verify(withdrawalRepository).save(withdrawalEntity);
  }

  @Test
  void testSaveScheduledWithdrawal() {
    withdrawalRepositoriesGateway.save(scheduledWithdrawalEntity);

    verify(scheduledWithdrawalRepository).save(scheduledWithdrawalEntity);
  }
}
