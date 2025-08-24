package com.nam.base.source.code.dtos.dto;

import com.nam.base.source.code.entities.Account;
import com.nam.base.source.code.enums.AccountIdentifier;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountIdentity {
    private Account account;
    private Boolean isInactive;
    private AccountIdentifier identity;
}
