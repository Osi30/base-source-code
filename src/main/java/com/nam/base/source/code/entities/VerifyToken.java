package com.nam.base.source.code.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "verify_token")
public class VerifyToken {
    @Id
    @Column(name = "token_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "token", nullable = false, unique = true)
    private String token;

    @Column(name = "expiryDate", nullable = false)
    private LocalDateTime expiryDate;

    @Column(name = "account_id", nullable = false)
    private String accountId;
}
