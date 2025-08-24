package com.nam.base.source.code.mappers;

import com.nam.base.source.code.dtos.request.AuthRequest;
import com.nam.base.source.code.entities.Account;

public interface AccountMapper {
    AuthRequest toAccountDTO(Account account);
    Account toAccount(AuthRequest authRequest);
    Account updateAccount(AuthRequest request, Account existedAccount);
}
