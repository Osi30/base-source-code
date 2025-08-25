package com.nam.base.source.code.repositories;

import com.nam.base.source.code.entities.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepo extends JpaRepository<RefreshToken, String> {
    RefreshToken findByAccount_Id(String accountId);
}
