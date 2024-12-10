package unit.controllers;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import org.caicedo.andres.common.enums.WithdrawalStatusEnum;
import org.caicedo.andres.withdrawal.controller.PerformWithdrawalController;
import org.caicedo.andres.withdrawal.controller.model.PerformWithdrawalRequest;
import org.caicedo.andres.withdrawal.controller.model.PerformWithdrawalResponse;
import org.caicedo.andres.withdrawal.controller.model.Withdrawal;
import org.caicedo.andres.withdrawal.factory.WithdrawalUseCaseFactory;
import org.caicedo.andres.withdrawal.usecase.PerformWithdrawalUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
class PerformWithdrawalControllerTest {

  private MockMvc mockMvc;

  @Mock private WithdrawalUseCaseFactory withdrawalUseCaseFactory;

  @Mock private PerformWithdrawalUseCase performWithdrawalUseCase;

  @InjectMocks private PerformWithdrawalController performWithdrawalController;

  private PerformWithdrawalRequest performWithdrawalRequest;

  @BeforeEach
  void setUp() {
    mockMvc = MockMvcBuilders.standaloneSetup(performWithdrawalController).build();

    performWithdrawalRequest =
        PerformWithdrawalRequest.builder()
            .userId(1L)
            .paymentMethodId(100L)
            .amount(BigDecimal.valueOf(500.00))
            .executeAt("ASAP")
            .build();
  }

  @Test
  void testPerformWithdrawal() throws Exception {
    Withdrawal mockWithdrawal =
        Withdrawal.builder()
            .id(1L)
            .transactionId(123L)
            .userId(1L)
            .paymentMethodId(100L)
            .amount(BigDecimal.valueOf(500.00))
            .status(WithdrawalStatusEnum.PENDING)
            .build();

    PerformWithdrawalResponse mockResponse =
        PerformWithdrawalResponse.builder().withdrawal(mockWithdrawal).build();

    when(withdrawalUseCaseFactory.getUseCase(anyString())).thenReturn(performWithdrawalUseCase);
    when(performWithdrawalUseCase.execute(any(PerformWithdrawalRequest.class)))
        .thenReturn(mockResponse);

    mockMvc
        .perform(
            post("/api/v1/withdrawals")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(performWithdrawalRequest)))
        .andExpect(status().isOk())
        .andDo(print())
        .andReturn();

    verify(withdrawalUseCaseFactory, times(1)).getUseCase(any());
    verify(performWithdrawalUseCase, times(1)).execute(any());
  }
}
