package com.nam.base.source.code.config;

import com.nam.base.source.code.entities.Account;
import com.nam.base.source.code.entities.Permission;
import com.nam.base.source.code.entities.Role;
import com.nam.base.source.code.enums.AccountStatus;
import com.nam.base.source.code.enums.DefaultPermission;
import com.nam.base.source.code.enums.DefaultRole;
import com.nam.base.source.code.repositories.AccountRepo;
import com.nam.base.source.code.repositories.PermissionRepo;
import com.nam.base.source.code.repositories.RoleRepo;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@Configuration
@RequiredArgsConstructor
public class DataInit {
    @Value("${ADMIN_EMAIL}")
    private String adminEmail;

    @Value("${ADMIN_PASSWORD}")
    private String adminPassword;

    private final PermissionRepo permissionRepo;
    private final RoleRepo roleRepo;
    private final AccountRepo accountRepo;
    private final PasswordEncoder passwordEncoder;

    // Run one time when application run
    @Bean
    ApplicationRunner applicationRunner() {
        return args -> {
            // Add Default Properties
            if (permissionRepo.findAll().isEmpty()) {
                // Permissions
                List<Permission> defaultPermissions = Arrays.stream(DefaultPermission.values())
                        .map(p -> Permission.builder()
                                .name(p.name())
                                .description(p.getDescription())
                                .build())
                        .toList();

                defaultPermissions = permissionRepo.saveAll(defaultPermissions);

                // Roles
                List<Permission> tempPermissions = defaultPermissions;
                List<Role> defaultRoles = Arrays.stream(DefaultRole.values())
                        .map(r -> Role.builder()
                                .roleName(r.getDetail())
                                .isDeleted(false)
                                .permissions(classifyPermission(tempPermissions, r))
                                .build())
                        .toList();

                defaultRoles = roleRepo.saveAll(defaultRoles);

                // Admin Account
                Account admin = Account.builder()
                        .email(adminEmail)
                        .password(passwordEncoder.encode(adminPassword))
                        .status(AccountStatus.ACTIVE)
                        .fullName("Admin")
                        .role(defaultRoles.stream()
                                .filter(r -> r.getRoleName().equals(DefaultRole.ADMIN.getDetail()))
                                .findFirst().orElseThrow(() -> new EntityNotFoundException("No admin role found"))
                        )
                        .build();

                accountRepo.save(admin);
            }
        };
    }

    private List<Permission> classifyPermission(List<Permission> permissions, DefaultRole role) {
        return switch (role) {
            case CUSTOMER -> Stream.of(
                    DefaultPermission.MANAGE_PROFILE.name()
            ).map(
                    pn -> permissions.stream()
                            .filter(p -> p.getName().equals(pn))
                            .findAny().orElseThrow(() -> new EntityNotFoundException("No permission found for " + pn))
            ).toList();
            case EMPLOYEE -> Stream.of(
                    DefaultPermission.MANAGE_PROFILE.name(),
                    DefaultPermission.VIEW_PROFILE_LIST.name(),
                    DefaultPermission.VIEW_ROLE_LIST.name()
            ).map(
                    pn -> permissions.stream()
                            .filter(p -> p.getName().equals(pn))
                            .findFirst().orElseThrow(() -> new EntityNotFoundException("No permission found for " + pn))
            ).toList();
            default -> permissions;
        };
    }
}
