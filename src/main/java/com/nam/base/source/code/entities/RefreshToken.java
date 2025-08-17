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
@Table(name = "refresh_token")
public class RefreshToken {
    @Id
    @Column(name = "refresh_token_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "token")
    private String token;

    @Column(name = "expired_at")
    private LocalDateTime expiresAt;

    @Column(name = "account_id")
    private String account;
}
