package com.faspix.deposit.repository;

import com.faspix.deposit.entity.Deposit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DepositRepository extends JpaRepository<Deposit, Long> {

    List<Deposit> findDepositsByEmail(String email);

}
