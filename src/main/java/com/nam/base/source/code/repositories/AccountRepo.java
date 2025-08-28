package com.nam.base.source.code.repositories;

import com.nam.base.source.code.entities.Account;
import com.nam.base.source.code.enums.AccountStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

public interface AccountRepo extends JpaRepository<Account, String> {
    Account findByEmail(String email);

    Account findByUsername(String username);

    Account findByPhoneNumber(String phoneNumber);

    Account findAccountById(String accountId);

    @Query("""
            SELECT a
            FROM Account a
            WHERE a.email = :identifier
            or a.username = :identifier
            OR a.phoneNumber = :identifier
            """)
    Account findByIdentifier(String identifier);

    List<Account> findAllByStatusIsIn(Collection<AccountStatus> status);
}
