package com.nam.base.source.code.services;

import com.nam.base.source.code.dtos.request.AuthRequest;

public interface AccountService {
    void register(AuthRequest authRequest);
}
