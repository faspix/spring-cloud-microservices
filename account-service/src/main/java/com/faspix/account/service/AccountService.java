package com.faspix.account.service;

import com.faspix.account.dto.AccountRequestDto;
import com.faspix.account.entity.Account;

public interface AccountService {

    Account getAccountById(Long accountId);

    Long createAccount(AccountRequestDto accountDto);

    Account updateAccount(Long accountId, AccountRequestDto accountDto);

    Account deleteAccount(Long accountId);

}
