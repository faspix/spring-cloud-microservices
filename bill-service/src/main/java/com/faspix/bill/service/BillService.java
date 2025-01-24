package com.faspix.bill.service;

import com.faspix.bill.dto.BillRequestDto;
import com.faspix.bill.entity.Bill;
import com.faspix.bill.exception.BillNotFoundException;
import com.faspix.bill.mapper.BillMapper;
import com.faspix.bill.repository.BillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
public class BillService {

    private final BillRepository billRepository;

    private final BillMapper billMapper;

    public Bill getBillById(Long billId) {
        return billRepository.findById(billId)
                .orElseThrow(() -> new BillNotFoundException("Unable to find bill with id " + billId));

    }

    public Long createBill(BillRequestDto billDto) {
        Bill bill = billMapper.billRequestToBill(billDto);
        bill.setCreationDate(OffsetDateTime.now());
        return billRepository.save(bill).getId();
    }

    public Bill updateBill(Long billId, BillRequestDto billDto) {
        getBillById(billId);
        Bill bill = billMapper.billRequestToBill(billDto);
        bill.setId(billId);
//        bill.setAccountId(billDto.getAccountId());
//        bill.setAmount(billDto.getAmount());
//        bill.setIsDefault(billDto.getIsDefault());
//        bill.setOverdraftEnabled(billDto.getOverdraftEnabled());
        return billRepository.save(bill);
    }

    public Bill deleteBill(Long billId) {
        Bill bill = getBillById(billId);
        billRepository.delete(bill);
        return bill;
    }

}
