package com.faspix.deposit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepositRequestDto {

    private Long accountId;

    private Long billId;

    private BigDecimal amount;
}
