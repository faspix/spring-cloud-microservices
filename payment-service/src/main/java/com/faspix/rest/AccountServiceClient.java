package com.faspix.rest;

import com.faspix.dto.AccountResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "account-service")
public interface AccountServiceClient {

    @GetMapping("/accounts/{accountId}")
    AccountResponseDto getAccountById(
            @PathVariable Long accountId
    );

}
