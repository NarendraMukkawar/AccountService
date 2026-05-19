package com.accountservice.repo;

import com.accountservice.entity.Account;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByEmail(String email);

    Optional<Account> findByMobileNumber(String mobileNumber);

    Optional<Account> findByAadharNumber(String aadharNumber);

    Optional<Account> findByPanNumber(String panNumber);

    Optional<Account> findByAccountNumber(String accountNumber);

    boolean existsByEmail(String email);

    boolean existsByMobileNumber(String mobileNumber);

    boolean existsByAadharNumber(String aadharNumber);

    boolean existsByPanNumber(String panNumber);

    boolean existsByAccountNumber(String accountNumber);

}