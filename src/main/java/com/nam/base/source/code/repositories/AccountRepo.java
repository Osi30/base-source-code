package com.nam.base.source.code.repositories;

import com.nam.base.source.code.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepo extends JpaRepository<Account, String> {
}
