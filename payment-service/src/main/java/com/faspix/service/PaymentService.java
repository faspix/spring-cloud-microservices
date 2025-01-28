package com.faspix.service;

import com.faspix.dto.PaymentRequestDto;
import com.faspix.dto.PaymentResponseDto;

public interface PaymentService {

    PaymentResponseDto payment(PaymentRequestDto paymentDto);

}
