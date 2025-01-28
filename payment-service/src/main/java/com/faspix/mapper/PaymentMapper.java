package com.faspix.mapper;

import com.faspix.dto.BillRequestDto;
import com.faspix.dto.BillResponseDto;
import com.faspix.dto.PaymentResponseDto;
import com.faspix.entity.Payment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    BillRequestDto BillResponseToBillRequest(BillResponseDto billResponseDto);

    PaymentResponseDto PaymentToResponseDto(Payment payment);

}
