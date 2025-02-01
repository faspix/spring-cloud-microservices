package com.faspix.payment.utils;

import com.faspix.dto.AccountResponseDto;
import com.faspix.dto.BillResponseDto;
import com.faspix.dto.PaymentRequestDto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

public class dto {

    public final static AccountResponseDto ACCOUNT_RESPONSE_DTO_TEST =
            new AccountResponseDto(1L,
                    "Name",
                    "email@mail",
                    List.of(1L),
                    "+12345678",
                    OffsetDateTime.now());

    public final static BillResponseDto BILL_RESPONSE_DTO_TEST =
            new BillResponseDto(1L,
                    1L,
                    BigDecimal.TEN,
                    true,
                    OffsetDateTime.now(),
                    true);

    public final static PaymentRequestDto PAYMENT_REQUEST_DTO_TEST =
            new PaymentRequestDto(1L, BigDecimal.TEN, 1L);


    public final static PaymentRequestDto PAYMENT_REQUEST_DTO_TEST_NO_BILL =
            new PaymentRequestDto(1L, BigDecimal.TEN, null);

}
