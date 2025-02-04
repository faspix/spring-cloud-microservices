package com.faspix.deposit.service;

import com.faspix.deposit.dto.*;

public interface DepositService {

    DepositResponseDto deposit(DepositRequestDto depositDto);

}
