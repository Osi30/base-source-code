package com.nam.base.source.code.services;

import com.nam.base.source.code.dtos.request.AccountRequest;
import com.nam.base.source.code.dtos.request.AuthRequest;
import com.nam.base.source.code.dtos.request.ResetPasswordRequest;
import com.nam.base.source.code.dtos.response.AccountResponse;
import com.nam.base.source.code.entities.Account;
import com.nam.base.source.code.enums.AccountIdentifier;

import java.util.List;

public interface AccountService {
    Account createAccount(AuthRequest authRequest);

    Account getAccountById(String accountId);

    Account getAccountByIdentifier(String identifier, AccountIdentifier identifierType);

    AccountResponse getAccountResponseById(String accountId);

    AccountResponse updateAccount(AccountRequest account, String accountId);

    String deleteAccount(String accountId);

    String resetPassword(String accountId, ResetPasswordRequest resetPasswordRequest);

    String banAccount(String accountId);

    List<Account> getAllAccounts();

    List<AccountResponse> getAllAccountResponses();
}
