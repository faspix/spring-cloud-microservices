package com.faspix.deposit.utils;

import com.faspix.deposit.dto.AccountResponseDto;
import com.faspix.deposit.dto.BillResponseDto;
import com.faspix.deposit.dto.DepositRequestDto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Arrays;

public class dto {

    public static AccountResponseDto createAccountResponseDto() {
        AccountResponseDto accountResponseDto = new AccountResponseDto();
        accountResponseDto.setAccountId(1L);
        accountResponseDto.setBills(Arrays.asList(1L, 2L, 3L));
        accountResponseDto.setCreationDate(OffsetDateTime.now());
        accountResponseDto.setEmail("mail@mail.com");
        accountResponseDto.setName("TestName");
        accountResponseDto.setPhone("+1234567890");
        return accountResponseDto;
    }

    public static BillResponseDto createBillResponseDto() {
        BillResponseDto billResponseDto = new BillResponseDto();
        billResponseDto.setId(1L);
        billResponseDto.setAccountId(1L);
        billResponseDto.setAmount(BigDecimal.valueOf(1000));
        billResponseDto.setIsDefault(true);
        billResponseDto.setCreationDate(OffsetDateTime.now());
        billResponseDto.setOverdraftEnabled(true);
        return billResponseDto;
    }

    public final static DepositRequestDto DEPOSIT_REQUEST_DTO =
            new DepositRequestDto(1L, 1L, BigDecimal.TEN);


}
