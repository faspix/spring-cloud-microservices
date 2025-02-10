package com.faspix.payment.controller;

import com.faspix.PaymentApplication;
import com.faspix.dto.PaymentResponseDto;
import com.faspix.entity.Payment;
import com.faspix.repository.PaymentRepository;
import com.faspix.rest.AccountServiceClient;
import com.faspix.rest.BillServiceClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static com.faspix.payment.utils.dto.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = {PaymentApplication.class})
@AutoConfigureMockMvc
public class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BillServiceClient billServiceClient;

    @MockitoBean
    private AccountServiceClient accountServiceClient;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PaymentRepository paymentRepository;

    @MockitoBean
    private RabbitTemplate rabbitTemplate;


    @Test
    public void paymentTest_withBillId() throws Exception {
        Mockito.when(billServiceClient.getBillById(anyLong()))
                .thenReturn(BILL_RESPONSE_DTO_TEST);
        Mockito.when(accountServiceClient.getAccountById(anyLong()))
                .thenReturn(ACCOUNT_RESPONSE_DTO_TEST);

        MvcResult mvcResult = mockMvc.perform(post("/payments")
                        .content(objectMapper.writeValueAsString(PAYMENT_REQUEST_DTO_TEST))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                ).andExpect(status().is2xxSuccessful())
                .andReturn();
        String body = mvcResult.getResponse().getContentAsString();
        PaymentResponseDto paymentResponseDto = objectMapper.readValue(body, PaymentResponseDto.class);

        List<Payment> payments = paymentRepository.findPaymentsByEmail(ACCOUNT_RESPONSE_DTO_TEST.getEmail());

        Assertions.assertEquals(payments.getFirst().getAmount().stripTrailingZeros(),
                paymentResponseDto.getAmount().stripTrailingZeros());
        Assertions.assertEquals(payments.getFirst().getEmail(), paymentResponseDto.getEmail());

    }

    @Test
    public void paymentTest_withAccountId() throws Exception {
        Mockito.when(billServiceClient.getBillById(anyLong()))
                .thenReturn(BILL_RESPONSE_DTO_TEST);
        Mockito.when(accountServiceClient.getAccountById(anyLong()))
                .thenReturn(ACCOUNT_RESPONSE_DTO_TEST);
        Mockito.when(billServiceClient.getBillsByAccountId(anyLong()))
                .thenReturn(List.of(BILL_RESPONSE_DTO_TEST));

        MvcResult mvcResult = mockMvc.perform(post("/payments")
                        .content(objectMapper.writeValueAsString(PAYMENT_REQUEST_DTO_TEST_NO_BILL))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                ).andExpect(status().is2xxSuccessful())
                .andReturn();
        String body = mvcResult.getResponse().getContentAsString();
        PaymentResponseDto paymentResponseDto = objectMapper.readValue(body, PaymentResponseDto.class);

        List<Payment> payments = paymentRepository.findPaymentsByEmail(ACCOUNT_RESPONSE_DTO_TEST.getEmail());

        Assertions.assertEquals(payments.getFirst().getAmount().stripTrailingZeros(),
                paymentResponseDto.getAmount().stripTrailingZeros());
        Assertions.assertEquals(payments.getFirst().getEmail(), paymentResponseDto.getEmail());

    }

}
