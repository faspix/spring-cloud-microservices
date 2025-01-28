package com.faspix.deposit.controller;

import com.faspix.deposit.dto.DepositRequestDto;
import com.faspix.deposit.dto.DepositResponseDto;
import com.faspix.deposit.service.DepositService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DepositController {

    private final DepositService depositService;

    @PostMapping("/deposits")
    public DepositResponseDto deposit(
            @RequestBody DepositRequestDto depositDto
    ) {
        return depositService.deposit(depositDto);
    }



}
