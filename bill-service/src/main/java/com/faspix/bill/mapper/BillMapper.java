package com.faspix.bill.mapper;

import com.faspix.bill.dto.BillRequestDto;
import com.faspix.bill.dto.BillResponseDto;
import com.faspix.bill.entity.Bill;
import org.mapstruct.Mapper;

@Mapper
public interface BillMapper {

    Bill billRequestToBill(BillRequestDto dto);

    BillResponseDto billToBillResponse(Bill bill);

}
