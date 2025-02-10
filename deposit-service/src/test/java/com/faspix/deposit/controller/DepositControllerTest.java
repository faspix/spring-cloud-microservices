package com.faspix.deposit.controller;

import com.faspix.deposit.DepositApplication;
import com.faspix.deposit.dto.AccountResponseDto;
import com.faspix.deposit.dto.BillResponseDto;
import com.faspix.deposit.dto.DepositResponseDto;
import com.faspix.deposit.entity.Deposit;
import com.faspix.deposit.repository.DepositRepository;
import com.faspix.deposit.rest.AccountServiceClient;
import com.faspix.deposit.rest.BillServiceClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static com.faspix.deposit.utils.dto.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = {DepositApplication.class})
public class DepositControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private DepositRepository depositRepository;

    @MockitoBean
    private BillServiceClient billServiceClient;

    @MockitoBean
    private AccountServiceClient accountServiceClient;

    @MockitoBean
    private RabbitTemplate rabbitTemplate;

    @BeforeEach
    public void init() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }


    @Test
    public void createDeposit() throws Exception {
        BillResponseDto billResponseDto = createBillResponseDto();
        AccountResponseDto accountResponseDto = createAccountResponseDto();
        Mockito.when(billServiceClient.getBillById(anyLong()))
                .thenReturn(billResponseDto);
        Mockito.when(accountServiceClient.getAccountById(anyLong()))
                .thenReturn(accountResponseDto);


        MvcResult mvcResult = mockMvc.perform(post("/deposits")
                        .content(objectMapper.writeValueAsString(DEPOSIT_REQUEST_DTO))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andReturn();

        String body = mvcResult.getResponse().getContentAsString();
        List<Deposit> deposits = depositRepository.findDepositsByEmail(accountResponseDto.getEmail());
        DepositResponseDto depositResponseDto = objectMapper.readValue(body, DepositResponseDto.class);
        Assertions.assertEquals(depositResponseDto.getEmail(), deposits.getFirst().getEmail());
        Assertions.assertEquals(depositResponseDto.getAmount().stripTrailingZeros(),
                deposits.getFirst().getAmount().stripTrailingZeros());
    }

}
