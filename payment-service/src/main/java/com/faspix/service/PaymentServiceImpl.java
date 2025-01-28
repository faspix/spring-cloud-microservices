package com.faspix.service;

import com.faspix.dto.*;
import com.faspix.entity.Payment;
import com.faspix.exception.PaymentServiceException;
import com.faspix.mapper.PaymentMapper;
import com.faspix.repository.PaymentRepository;
import com.faspix.rest.AccountServiceClient;
import com.faspix.rest.BillServiceClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private static final String TOPIC_EXCHANGE_PAYMENT = "js.payment.notify.exchange";
    private static final String ROUTING_KEY_PAYMENT = "js.key.payment";

    private final PaymentMapper paymentMapper;

    private final PaymentRepository paymentRepository;

    private final BillServiceClient billServiceClient;

    private final AccountServiceClient accountServiceClient;

    private final RabbitTemplate rabbitTemplate;

    @Override
    @Transactional
    public PaymentResponseDto payment(PaymentRequestDto paymentDto) {
        if (paymentDto.getAccountId() == null && paymentDto.getBillId() == null) {
            throw new PaymentServiceException("Account is null and bill is null");
        }

        if (paymentDto.getBillId() != null) {
            return createResponse(paymentDto.getBillId(), paymentDto.getAmount());
        }

        BillResponseDto defaultBill = getDefaultBill(paymentDto.getAccountId());
        return createResponse(defaultBill.getId(), paymentDto.getAmount());

    }

    private PaymentResponseDto createResponse(Long billId, BigDecimal amount) {
        BillResponseDto billDto = billServiceClient.getBillById(billId);
        BillRequestDto newBill = paymentMapper.BillResponseToBillRequest(billDto);

        newBill.setAmount(newBill.getAmount().subtract(amount));
        billServiceClient.updateBill(billId, newBill);

        AccountResponseDto accountResp = accountServiceClient.getAccountById(newBill.getAccountId());
        Payment payment = new Payment(
                amount, billId, OffsetDateTime.now(), accountResp.getEmail()
        );
        paymentRepository.save(payment);
        PaymentResponseDto responseDto = paymentMapper.PaymentToResponseDto(payment);

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            rabbitTemplate.convertAndSend(ROUTING_KEY_PAYMENT, TOPIC_EXCHANGE_PAYMENT,
                    objectMapper.writeValueAsString(responseDto));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new PaymentServiceException("Can't sand message to RabbitMQ");
        }

        return responseDto;
    }

    private BillResponseDto getDefaultBill(Long accountId) {
        return billServiceClient.getBillsByAccountId(accountId)
                .stream()
                .filter(BillResponseDto::getIsDefault)
                .findFirst()
                .orElseThrow(() ->
                        new PaymentServiceException("Unable to find default bill for account with id " + accountId)
                );
    }

}
