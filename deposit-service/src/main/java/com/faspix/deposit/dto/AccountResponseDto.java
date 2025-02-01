package com.faspix.deposit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.List;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class AccountResponseDto {

    private Long accountId;

    private String name;

    private String email;

    private List<Long> bills;

    private String phone;

    private OffsetDateTime creationDate;

}
