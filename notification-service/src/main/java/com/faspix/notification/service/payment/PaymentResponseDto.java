package com.faspix.notification.service.payment;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
public class PaymentResponseDto {

    private Long paymentId;

    private BigDecimal amount;

    private Long billId;

    private OffsetDateTime creationDate;

    private String email;

}
