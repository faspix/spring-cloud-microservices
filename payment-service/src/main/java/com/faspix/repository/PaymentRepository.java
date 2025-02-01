package com.faspix.repository;

import com.faspix.dto.PaymentResponseDto;
import com.faspix.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    List<Payment> findPaymentsByEmail(String email);

}
