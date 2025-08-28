package com.nam.base.source.code.mappers;

import com.nam.base.source.code.dtos.request.AccountRequest;
import com.nam.base.source.code.dtos.request.AuthRequest;
import com.nam.base.source.code.dtos.response.AccountResponse;
import com.nam.base.source.code.entities.Account;

public interface AccountMapper {
    Account toAccount(AuthRequest authRequest);
    Account updateAccount(AuthRequest request, Account existedAccount);
    Account updateAccount(AccountRequest request, Account existedAccount);
    AccountResponse toAccountResponse(Account account);
}
