package com.faspix.account.mapper;

import com.faspix.account.dto.AccountRequestDto;
import com.faspix.account.dto.AccountResponseDto;
import com.faspix.account.entity.Account;
import org.mapstruct.Mapper;

@Mapper
public interface AccountMapper {

    Account accountRequestToAccount(AccountRequestDto dto);

    AccountResponseDto accountToAccountResponse(Account account);

}
