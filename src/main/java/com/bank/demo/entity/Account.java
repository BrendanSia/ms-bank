package com.bank.demo.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name = "cust_id", columnDefinition = "BINARY(16)", nullable = false)
    private UUID custId;

    @Column(name = "acc_no", nullable = false)
    private String accNo;
}
