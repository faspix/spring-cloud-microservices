package com.faspix.deposit;

import com.faspix.deposit.dto.*;
import com.faspix.deposit.entity.Deposit;
import com.faspix.deposit.exception.DepositServiceException;
import com.faspix.deposit.mapper.DepositMapper;
import com.faspix.deposit.repository.DepositRepository;
import com.faspix.deposit.rest.AccountServiceClient;
import com.faspix.deposit.rest.BillServiceClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
public class DepositService {

    private static final String TOPIC_EXCHANGE_DEPOSIT = "js.deposit.notify.exchange";
    private static final String ROUTING_KEY_DEPOSIT = "js.key.deposit";

    private final DepositRepository depositRepository;

    private final DepositMapper depositMapper;

    private final AccountServiceClient accountServiceClient;

    private final BillServiceClient billServiceClient;

    private final RabbitTemplate rabbitTemplate;


    public DepositResponseDto deposit(DepositRequestDto depositDto) {
        if (depositDto.getAccountId() == null && depositDto.getBillId() == null)
            throw new DepositServiceException("Account is null and bill is null");

        if (depositDto.getBillId() != null) {
            return createResponse(depositDto.getBillId(), depositDto.getAmount());
        }

        BillResponseDto defaultBull = getDefaultBill(depositDto.getAccountId());
        return createResponse(defaultBull.getId(), depositDto.getAmount());

    }

    private DepositResponseDto createResponse(Long billId, BigDecimal amount) {
        BillResponseDto existBill = billServiceClient.getBillById(billId);
        BillRequestDto newBill = depositMapper.ResponseToRequest(existBill);

        newBill.setAmount(existBill.getAmount().add(amount));
        billServiceClient.updateBill(billId, newBill);

        AccountResponseDto accountResponseDto = accountServiceClient.getAccountById(existBill.getAccountId());
        Deposit deposit = new Deposit(amount, billId,
                OffsetDateTime.now(), accountResponseDto.getEmail());
        depositRepository.save(deposit);

        DepositResponseDto depositResponseDto = depositMapper.DepositToResponseDto(deposit);

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            rabbitTemplate.convertAndSend(TOPIC_EXCHANGE_DEPOSIT, ROUTING_KEY_DEPOSIT,
                    objectMapper.writeValueAsString(depositResponseDto));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new DepositServiceException("Can't send message to RabbitMQ");
        }
        return depositResponseDto;
    }


    private BillResponseDto getDefaultBill(Long accountId) {
        return billServiceClient.getBillsByAccountId(accountId)
                .stream()
                .filter(BillResponseDto::getIsDefault)
                .findAny()
                .orElseThrow(() ->
                        new DepositServiceException("Unable to find default bill for account id " + accountId)
                );

    }

}
