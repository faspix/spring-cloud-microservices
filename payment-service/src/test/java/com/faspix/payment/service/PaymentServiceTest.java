package com.faspix.payment.service;

import com.faspix.dto.PaymentRequestDto;
import com.faspix.exception.PaymentServiceException;
import com.faspix.mapper.PaymentMapper;
import com.faspix.repository.PaymentRepository;
import com.faspix.rest.AccountServiceClient;
import com.faspix.rest.BillServiceClient;
import com.faspix.service.PaymentService;
import com.faspix.service.PaymentServiceImpl;
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
import java.util.Optional;

import static com.faspix.payment.utils.dto.*;
import static org.mockito.ArgumentMatchers.anyLong;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceTest {

    @InjectMocks
    private PaymentServiceImpl paymentService;

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private AccountServiceClient accountServiceClient;

    @Mock
    private BillServiceClient billServiceClient;

    @Mock
    private RabbitTemplate rabbitTemplate;

    @Spy
    private PaymentMapper paymentMapper = Mappers.getMapper(PaymentMapper.class);

    @Test
    void testPayment_withBillId() {
        Mockito.when(billServiceClient.getBillById(anyLong()))
                .thenReturn(BILL_RESPONSE_DTO_TEST);
        Mockito.when(accountServiceClient.getAccountById(anyLong()))
                .thenReturn(ACCOUNT_RESPONSE_DTO_TEST);

        var result = paymentService.payment(PAYMENT_REQUEST_DTO_TEST);

        Assertions.assertEquals(result.getAmount(), PAYMENT_REQUEST_DTO_TEST.getAmount());
    }


    @Test
    void testPayment_withAccountId() {
        Mockito.when(billServiceClient.getBillById(anyLong()))
                .thenReturn(BILL_RESPONSE_DTO_TEST);
        Mockito.when(accountServiceClient.getAccountById(anyLong()))
                .thenReturn(ACCOUNT_RESPONSE_DTO_TEST);
        Mockito.when(billServiceClient.getBillsByAccountId(anyLong()))
                .thenReturn(List.of(BILL_RESPONSE_DTO_TEST));

        var result = paymentService.payment(PAYMENT_REQUEST_DTO_TEST_NO_BILL);

        Assertions.assertEquals(result.getAmount(), PAYMENT_REQUEST_DTO_TEST.getAmount());
    }

    @Test
    void testPayment_AccountAndBillIsNullErrorTest() {
        Assertions.assertThrows(PaymentServiceException.class, () ->
            paymentService.payment(new PaymentRequestDto(null, BigDecimal.TEN, null))
        );
    }

}
