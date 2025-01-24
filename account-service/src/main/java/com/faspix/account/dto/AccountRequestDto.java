package com.faspix.account.dto;

import lombok.Data;

import java.time.OffsetDateTime;
import java.util.List;

@Data
public class AccountRequestDto {

    private String name;

    private String email;

    private String phone;

    private List<Long> bills;

    private OffsetDateTime creationDate;
}
