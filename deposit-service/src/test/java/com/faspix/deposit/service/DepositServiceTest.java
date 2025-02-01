package com.faspix.deposit.service;

import com.faspix.deposit.dto.*;
import com.faspix.deposit.exception.DepositServiceException;
import com.faspix.deposit.mapper.DepositMapper;
import com.faspix.deposit.repository.DepositRepository;
import com.faspix.deposit.rest.AccountServiceClient;
import com.faspix.deposit.rest.BillServiceClient;
import com.faspix.deposit.DepositService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.math.BigDecimal;
import java.util.List;

import static com.faspix.deposit.utils.dto.createAccountResponseDto;
import static com.faspix.deposit.utils.dto.createBillResponseDto;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@ExtendWith(MockitoExtension.class)
public class DepositServiceTest {

    @InjectMocks
    private DepositService depositService;

    @Mock
    private DepositRepository depositRepository;

    @Mock
    private AccountServiceClient accountServiceClient;

    @Mock
    private BillServiceClient billServiceClient;

    @Mock
    private RabbitTemplate rabbitTemplate;

    @Spy
    private DepositMapper depositMapper = Mappers.getMapper(DepositMapper.class);


    @Test
    public void depositServiceTest_withBillId() {
        BillResponseDto billDtoMock = createBillResponseDto();
        Mockito.when(billServiceClient.getBillById(anyLong()))
                .thenReturn(billDtoMock);
        AccountResponseDto accountDtoMock = createAccountResponseDto();
        Mockito.when(accountServiceClient.getAccountById(anyLong()))
                .thenReturn(accountDtoMock);
        DepositRequestDto depositRequestDto = new DepositRequestDto(null, 1L, BigDecimal.TEN);
        DepositResponseDto deposit = depositService.deposit(depositRequestDto);
        Assertions.assertEquals(deposit.getEmail(), accountDtoMock.getEmail());
    }

    @Test
    public void depositServiceTest_withAccountId() {
        BillResponseDto billDtoMock = createBillResponseDto();
        Mockito.when(billServiceClient.getBillById(anyLong()))
                .thenReturn(billDtoMock);
        AccountResponseDto accountDtoMock = createAccountResponseDto();
        Mockito.when(accountServiceClient.getAccountById(anyLong()))
                .thenReturn(accountDtoMock);
        Mockito.when(billServiceClient.getBillsByAccountId(anyLong()))
                .thenReturn(List.of(billDtoMock));

        DepositRequestDto depositRequestDto = new DepositRequestDto(1L, null, BigDecimal.TEN);
        DepositResponseDto deposit = depositService.deposit(depositRequestDto);
        Assertions.assertEquals(deposit.getEmail(), accountDtoMock.getEmail());
    }

    @Test
    public void setDepositServiceTest_exception() {
        Assertions.assertThrows(DepositServiceException.class, () ->
        depositService.deposit(new DepositRequestDto(null, null, BigDecimal.TEN))
        );
    }




}
