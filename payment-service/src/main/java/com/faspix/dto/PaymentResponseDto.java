package com.faspix.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
public class PaymentResponseDto {

    private BigDecimal amount;

    private String email;

}
