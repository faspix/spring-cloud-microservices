package com.faspix.deposit.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class DepositRequestDto {

    private Long accountId;

    private Long billId;

    private BigDecimal amount;
}
