package com.faspix.account.controller;

import com.faspix.account.dto.AccountRequestDto;
import com.faspix.account.dto.AccountResponseDto;
import com.faspix.account.entity.Account;
import com.faspix.account.mapper.AccountMapper;
import com.faspix.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    private final AccountMapper accountMapper;

    @GetMapping("{accountId}")
    public AccountResponseDto getAccount(
            @PathVariable Long accountId
    ) {
        Account account = accountService.getAccountById(accountId);
        return accountMapper.accountToAccountResponse(account);
    }

    @PostMapping
    public Long createAccount(
            @RequestBody AccountRequestDto accountDto
    ){
        return accountService.createAccount(accountDto);
    }

    @PutMapping("{accountId}")
    public AccountResponseDto updateAccount(
            @PathVariable Long accountId,
            @RequestBody AccountRequestDto accountDto
    ) {
        Account account = accountService.updateAccount(accountId, accountDto);
        return accountMapper.accountToAccountResponse(account);
    }

    @DeleteMapping("{accountId}")
    public AccountResponseDto deleteAccount(
            @PathVariable Long accountId
    ) {
        Account account = accountService.deleteAccount(accountId);
        return accountMapper.accountToAccountResponse(account);
    }


}
