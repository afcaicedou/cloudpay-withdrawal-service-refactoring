package unit.gateways;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Optional;
import org.caicedo.andres.user.repository.PaymentMethodRepository;
import org.caicedo.andres.user.repository.gateway.PaymentMethodRepositoryGateway;
import org.caicedo.andres.user.repository.model.PaymentMethodEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PaymentMethodRepositoryGatewayTest {

  @InjectMocks private PaymentMethodRepositoryGateway paymentMethodRepositoryGateway;

  @Mock private PaymentMethodRepository paymentMethodRepository;

  private PaymentMethodEntity paymentMethodEntity;

  @BeforeEach
  void setUp() {
    paymentMethodEntity = PaymentMethodEntity.builder().id(1L).name("Credit Card").build();
  }

  @Test
  void testFindById_found() {
    when(paymentMethodRepository.findById(1L)).thenReturn(Optional.of(paymentMethodEntity));

    Optional<PaymentMethodEntity> result = paymentMethodRepositoryGateway.findById(1L);

    assertEquals(paymentMethodEntity, result.orElse(null));
  }

  @Test
  void testFindById_notFound() {
    when(paymentMethodRepository.findById(1L)).thenReturn(Optional.empty());

    Optional<PaymentMethodEntity> result = paymentMethodRepositoryGateway.findById(1L);

    assertEquals(Optional.empty(), result);
  }
}
