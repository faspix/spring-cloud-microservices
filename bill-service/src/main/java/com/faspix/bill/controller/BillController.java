package com.faspix.bill.controller;

import com.faspix.bill.dto.BillRequestDto;
import com.faspix.bill.dto.BillResponseDto;
import com.faspix.bill.entity.Bill;
import com.faspix.bill.mapper.BillMapper;
import com.faspix.bill.service.BillService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class BillController {

    private final BillService billService;

    private final BillMapper billMapper;

    @GetMapping("{billId}")
    public BillResponseDto getBill(
            @PathVariable Long billId
    ) {
        Bill bill = billService.getBillById(billId);
        return billMapper.billToBillResponse(bill);
    }

    @PostMapping
    public Long createBill(
            @RequestBody BillRequestDto billDto
    ) {
        return billService.createBill(billDto);
    }

    @PutMapping("{billId}")
    public BillResponseDto updateBill(
            @PathVariable Long billId,
            @RequestBody BillRequestDto billDto
    ) {
        Bill bill = billService.updateBill(billId, billDto);
        return billMapper.billToBillResponse(bill);
    }

    @DeleteMapping("{billId}")
    public BillResponseDto deleteBill(
            @PathVariable Long billId
    ) {
        Bill bill = billService.deleteBill(billId);
        return billMapper.billToBillResponse(bill);
    }

}
