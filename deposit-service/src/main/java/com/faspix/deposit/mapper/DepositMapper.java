package com.faspix.deposit.mapper;

import com.faspix.deposit.dto.BillRequestDto;
import com.faspix.deposit.dto.BillResponseDto;
import com.faspix.deposit.dto.DepositResponseDto;
import com.faspix.deposit.entity.Deposit;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DepositMapper {

    BillRequestDto ResponseToRequest(BillResponseDto dto);

    DepositResponseDto DepositToResponseDto(Deposit deposit);

}
