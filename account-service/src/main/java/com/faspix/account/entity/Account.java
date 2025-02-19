package com.faspix.account.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private OffsetDateTime creationDate;

    @ElementCollection
    @CollectionTable(name = "account_bill",
            joinColumns = @JoinColumn(name = "account_id"))
    @Column(name = "bill_id", nullable = false)
    private List<Long> bills;



}
