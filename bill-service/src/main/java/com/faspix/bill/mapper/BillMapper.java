package com.faspix.bill.mapper;

import com.faspix.bill.dto.BillRequestDto;
import com.faspix.bill.dto.BillResponseDto;
import com.faspix.bill.entity.Bill;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BillMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "creationDate", ignore = true)
    Bill billRequestToBill(BillRequestDto dto);

    BillResponseDto billToBillResponse(Bill bill);

}
