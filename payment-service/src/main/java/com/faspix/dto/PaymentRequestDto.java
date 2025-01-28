package com.faspix.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class PaymentRequestDto {

    private Long accountId;

    private BigDecimal amount;

    private Long billId;

}
