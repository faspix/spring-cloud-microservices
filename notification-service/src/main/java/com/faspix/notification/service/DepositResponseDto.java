package com.faspix.notification.service;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class DepositResponseDto {

    private BigDecimal amount;

    private String email;

}
