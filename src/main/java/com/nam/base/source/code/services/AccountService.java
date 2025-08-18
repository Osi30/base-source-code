package com.nam.base.source.code.services;

import com.nam.base.source.code.dtos.request.AuthRequest;
import com.nam.base.source.code.entities.Account;

public interface AccountService {
    Account register(AuthRequest authRequest);
}
