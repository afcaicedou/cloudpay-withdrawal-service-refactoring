package unit.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import org.caicedo.andres.user.controller.mapper.UserMapper;
import org.caicedo.andres.user.controller.model.User;
import org.caicedo.andres.user.repository.model.PaymentMethodEntity;
import org.caicedo.andres.user.repository.model.UserEntity;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class UserMapperTest {

  private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);

  @Test
  void testToDomain_singleEntity() {
    PaymentMethodEntity paymentMethodEntity =
        PaymentMethodEntity.builder().id(1L).name("Credit Card").build();

    UserEntity userEntity =
        UserEntity.builder()
            .id(1L)
            .firstName("John")
            .paymentMethods(Collections.singletonList(paymentMethodEntity))
            .maxWithdrawalAmount(BigDecimal.valueOf(1000))
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();

    User user = userMapper.toDomain(userEntity);

    assertEquals(userEntity.getId(), user.getId());
    assertEquals(userEntity.getFirstName(), user.getFirstName());
    assertEquals(userEntity.getMaxWithdrawalAmount(), user.getMaxWithdrawalAmount());
    assertEquals(userEntity.getPaymentMethods().size(), user.getPaymentMethods().size());
    assertEquals(
        userEntity.getPaymentMethods().getFirst().getName(),
        user.getPaymentMethods().getFirst().getName());
  }

  @Test
  void testToDomain_listEntities() {
    PaymentMethodEntity paymentMethodEntity1 =
        PaymentMethodEntity.builder().id(1L).name("Credit Card").build();
    PaymentMethodEntity paymentMethodEntity2 =
        PaymentMethodEntity.builder().id(2L).name("PayPal").build();

    UserEntity userEntity1 =
        UserEntity.builder()
            .id(1L)
            .firstName("John")
            .paymentMethods(List.of(paymentMethodEntity1))
            .maxWithdrawalAmount(BigDecimal.valueOf(1000))
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();
    UserEntity userEntity2 =
        UserEntity.builder()
            .id(2L)
            .firstName("Jane")
            .paymentMethods(List.of(paymentMethodEntity2))
            .maxWithdrawalAmount(BigDecimal.valueOf(2000))
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();

    List<UserEntity> userEntities = List.of(userEntity1, userEntity2);

    List<User> users = userMapper.toDomain(userEntities);

    assertEquals(2, users.size());
    assertEquals(userEntities.get(0).getId(), users.get(0).getId());
    assertEquals(userEntities.get(1).getId(), users.get(1).getId());
    assertEquals(userEntities.get(0).getFirstName(), users.get(0).getFirstName());
    assertEquals(userEntities.get(1).getFirstName(), users.get(1).getFirstName());
    assertEquals(
        userEntities.get(0).getPaymentMethods().size(), users.get(0).getPaymentMethods().size());
    assertEquals(
        userEntities.get(1).getPaymentMethods().size(), users.get(1).getPaymentMethods().size());
  }
}
