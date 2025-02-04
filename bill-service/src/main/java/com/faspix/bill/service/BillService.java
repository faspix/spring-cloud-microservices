package com.faspix.bill.service;

import com.faspix.bill.dto.BillRequestDto;
import com.faspix.bill.entity.Bill;

import java.util.List;

public interface BillService {

    Bill getBillById(Long billId);

    Long createBill(BillRequestDto billDto);

    Bill updateBill(Long billId, BillRequestDto billDto);

    Bill deleteBill(Long billId);

    List<Bill> getBillsByAccountId(Long accountId);

}
