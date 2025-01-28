package com.faspix.rest;

import com.faspix.dto.BillRequestDto;
import com.faspix.dto.BillResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "bill-service")
public interface BillServiceClient {

    @GetMapping(value = "/bills/{billId}")
    BillResponseDto getBillById(
            @PathVariable Long billId
    );

    @GetMapping("bills/account/{accountId}")
    List<BillResponseDto> getBillsByAccountId(
            @PathVariable Long accountId
    );

    @PutMapping("/bills/{billId}")
    BillResponseDto updateBill(
            @PathVariable Long billId,
            @RequestBody BillRequestDto billDto
    );

}
