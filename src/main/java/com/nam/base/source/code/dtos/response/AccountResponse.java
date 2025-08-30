package com.nam.base.source.code.dtos.response;

import com.nam.base.source.code.entities.Role;
import com.nam.base.source.code.enums.AccountStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountResponse {
    private String id;
    private String email;
    private String username;
    private String fullName;
    private String phoneNumber;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private Role accountRole;
    private AccountStatus status;
}
