package com.bank.demo.model;

import lombok.*;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountDTO {
    private UUID id;
    private UUID custId;
    private String accNo;
}
