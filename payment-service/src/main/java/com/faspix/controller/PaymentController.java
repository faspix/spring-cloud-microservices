package com.faspix.controller;

import com.faspix.dto.PaymentRequestDto;
import com.faspix.dto.PaymentResponseDto;
import com.faspix.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/payments")
    public PaymentResponseDto payment(
            @RequestBody PaymentRequestDto paymentDto
    ) {
        return paymentService.payment(paymentDto);
    }

}
