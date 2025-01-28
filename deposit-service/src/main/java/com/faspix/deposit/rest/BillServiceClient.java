package com.faspix.deposit.rest;

import com.faspix.deposit.dto.BillRequestDto;
import com.faspix.deposit.dto.BillResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name = "bill-service")
public interface BillServiceClient {

    @RequestMapping(value = "/bills/{billId}", method = RequestMethod.GET)
    BillResponseDto getBillById(
            @PathVariable Long billId
    );

    @RequestMapping(value = "/bills/{billId}", method = RequestMethod.PUT)
    BillResponseDto updateBill(
            @PathVariable Long billId,
            @RequestBody BillRequestDto billDto
    );

    @RequestMapping(value = "/bills/account/{accountId}", method = RequestMethod.GET)
    List<BillResponseDto> getBillsByAccountId(
            @PathVariable Long accountId
    );

}
