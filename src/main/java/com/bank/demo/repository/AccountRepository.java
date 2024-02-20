package com.bank.demo.repository;

import com.bank.demo.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, UUID> {

    @Query(value = "SELECT a FROM Account a WHERE a.custId = :customerId")
    Account findByCustomerId(@Param("customerId") UUID customerId);
    @Modifying
    @Query("DELETE FROM Account a WHERE a.custId = :customerId")
    void deleteByCustId(@Param("customerId") UUID customerId);
}
