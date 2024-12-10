package integration;

import org.caicedo.andres.WithdrawalServiceApp;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(classes = WithdrawalServiceApp.class)
public class WithdrawalServiceAppTest {

  @Test
  void mainMethodTest() {
    WithdrawalServiceApp.main(new String[] {});
  }
}
