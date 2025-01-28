package com.faspix.bill.repository;

import com.faspix.bill.entity.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillRepository extends JpaRepository<Bill, Long> {

    List<Bill> getBillsByAccountId(Long accountId);

}
