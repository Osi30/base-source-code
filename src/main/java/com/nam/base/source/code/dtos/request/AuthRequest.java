package com.nam.base.source.code.dtos.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nam.base.source.code.enums.AccountStatus;
import com.nam.base.source.code.enums.AuthType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthRequest {
    @JsonProperty("accountId")
    private String accountId;

    @JsonProperty("username")
    private String username;

    @JsonProperty("password")
    private String password;

    @JsonProperty("fullName")
    private String fullName;

    @Email
    @JsonProperty("email")
    private String email;

    @Pattern(regexp = "(84|0[3|5|7|8|9])+(\\d{8})", message = "Invalid phone!")
    @JsonProperty("phoneNumber")
    private String phoneNumber;

    @JsonProperty("accountRoleId")
    private String accountRoleId;

    @JsonProperty("identifier")
    private String identifier;

    @JsonProperty("accountStatus")
    private AccountStatus accountStatus;

    @JsonProperty("identifierType")
    private AuthType authType;
}
