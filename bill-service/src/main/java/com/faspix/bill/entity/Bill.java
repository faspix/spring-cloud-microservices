package com.faspix.bill.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Data
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long accountId;

    private BigDecimal amount;

    private Boolean isDefault;

    private OffsetDateTime creationDate;

    private Boolean overdraftEnabled;

}
