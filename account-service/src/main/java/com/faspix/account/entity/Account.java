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

    private String name;

    private String email;

    private String phone;

    private OffsetDateTime creationDate;

    @ElementCollection
    @CollectionTable(name = "account_bills",
            joinColumns = @JoinColumn(name = "account_id"))
    @Column(name = "bill_id")
    private List<Long> bills;



}
