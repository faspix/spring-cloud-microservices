package com.faspix.account.service;

import com.faspix.account.dto.AccountRequestDto;
import com.faspix.account.entity.Account;
import com.faspix.account.exception.AccountNotFoundException;
import com.faspix.account.mapper.AccountMapper;
import com.faspix.account.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    private final AccountMapper accountMapper;

    public Account getAccountById(Long accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException("Unable to find account with id " + accountId)
                );
    }

    public Long createAccount(AccountRequestDto accountDto) {
        Account account = accountMapper.accountRequestToAccount(accountDto);
        account.setCreationDate(OffsetDateTime.now());
        return accountRepository.save(account).getAccountId();
    }

    public Account updateAccount(Long accountId, AccountRequestDto accountDto) {
        Account account = getAccountById(accountId);
        account.setName(accountDto.getName());
        account.setPhone(accountDto.getPhone());
        account.setEmail(accountDto.getEmail());
        account.setBills(accountDto.getBills());
        return accountRepository.save(account);
    }

    public Account deleteAccount(Long accountId) {
        Account account = getAccountById(accountId);
        accountRepository.delete(account);
        return account;
    }

}
