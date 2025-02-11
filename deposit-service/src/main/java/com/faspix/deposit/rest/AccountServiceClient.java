package com.faspix.deposit.rest;

import com.faspix.deposit.dto.AccountResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "account-service")
public interface AccountServiceClient {

    @RequestMapping(value = "/accounts/{accountId}", method = RequestMethod.GET)
    AccountResponseDto getAccountById(
            @PathVariable Long accountId
    );

}
