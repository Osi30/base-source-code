package com.nam.base.source.code.repositories;

import com.nam.base.source.code.entities.VerifyToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerifyTokenRepo extends JpaRepository<VerifyToken, String> {
    VerifyToken findByToken(String token);
    VerifyToken findByAccount_Id(String accountId);
}
