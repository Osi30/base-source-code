package com.nam.base.source.code.services.impl;

import com.nam.base.source.code.repositories.AccountRepo;
import com.nam.base.source.code.services.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepo accountRepo;
}
