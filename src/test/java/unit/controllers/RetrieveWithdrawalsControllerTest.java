package unit.controllers;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import org.caicedo.andres.withdrawal.controller.RetrieveWithdrawalsController;
import org.caicedo.andres.withdrawal.controller.model.RetrieveWithdrawalsResponse;
import org.caicedo.andres.withdrawal.controller.model.Withdrawal;
import org.caicedo.andres.withdrawal.usecase.RetrieveWithdrawalsUseCase;
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
class RetrieveWithdrawalsControllerTest {

  @Mock private RetrieveWithdrawalsUseCase retrieveWithdrawalsUseCase;

  @InjectMocks private RetrieveWithdrawalsController retrieveWithdrawalsController;

  private MockMvc mockMvc;

  @BeforeEach
  void setup() {
    mockMvc = MockMvcBuilders.standaloneSetup(retrieveWithdrawalsController).build();
  }

  @Test
  void testRetrieveWithdrawals() throws Exception {
    RetrieveWithdrawalsResponse mockResponse =
        RetrieveWithdrawalsResponse.builder()
            .withdrawals(List.of(Withdrawal.builder().id(1L).build()))
            .build();
    when(retrieveWithdrawalsUseCase.execute()).thenReturn(mockResponse);

    mockMvc
        .perform(get("/api/v1/withdrawals").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON));
  }
}
