package com.nam.base.source.code.services;

import com.nam.base.source.code.dtos.request.AccountRequest;
import com.nam.base.source.code.dtos.request.AuthRequest;
import com.nam.base.source.code.dtos.response.AccountResponse;
import com.nam.base.source.code.entities.Account;

import java.util.List;

public interface AccountService {
    Account createAccount(AuthRequest authRequest);

    Account getAccountById(String accountId);

    Account getAccountByIdentifier(String identifier);

    AccountResponse getAccountResponseById(String accountId);

    AccountResponse updateAccount(AccountRequest account, String accountId);

    String deleteAccount(String accountId);

    List<Account> getAllAccounts();

    List<AccountResponse> getAllAccountResponses();
}
