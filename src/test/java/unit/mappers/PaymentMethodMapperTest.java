package unit.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.caicedo.andres.user.controller.mapper.PaymentMethodMapper;
import org.caicedo.andres.user.controller.model.PaymentMethod;
import org.caicedo.andres.user.repository.model.PaymentMethodEntity;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class PaymentMethodMapperTest {

  private final PaymentMethodMapper paymentMethodMapper =
      Mappers.getMapper(PaymentMethodMapper.class);

  @Test
  void testToDomain() {
    PaymentMethodEntity paymentMethodEntity =
        PaymentMethodEntity.builder().id(1L).name("Credit Card").build();

    PaymentMethod paymentMethod = paymentMethodMapper.toDomain(paymentMethodEntity);

    assertEquals(paymentMethodEntity.getId(), paymentMethod.getId());
    assertEquals(paymentMethodEntity.getName(), paymentMethod.getName());
  }
}
