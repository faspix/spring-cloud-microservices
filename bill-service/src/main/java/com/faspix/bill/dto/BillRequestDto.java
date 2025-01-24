package com.faspix.bill.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BillRequestDto {

    private Long accountId;

    private BigDecimal amount;

    private Boolean isDefault;

    private Boolean overdraftEnabled;

}
