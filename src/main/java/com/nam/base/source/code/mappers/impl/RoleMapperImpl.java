package com.nam.base.source.code.mappers.impl;

import com.nam.base.source.code.dtos.request.RoleRequest;
import com.nam.base.source.code.dtos.response.RoleResponse;
import com.nam.base.source.code.entities.Role;
import com.nam.base.source.code.mappers.RoleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleMapperImpl implements RoleMapper {
    @Override
    public Role toRole(RoleRequest roleRequest) {
        return Role.builder()
                .roleName(roleRequest.getRoleName())
                .isDeleted(false)
                .build();
    }

    @Override
    public RoleResponse toRoleResponse(Role role) {
        return RoleResponse.builder()
                .id(role.getId())
                .roleName(role.getRoleName())
                .isDeleted(role.getIsDeleted())
                .permissions(role.getPermissions())
                .build();
    }

    @Override
    public Role updateRole(RoleRequest roleRequest, Role existingRole) {
        Optional.ofNullable(roleRequest.getRoleName()).ifPresent(existingRole::setRoleName);
        Optional.ofNullable(roleRequest.getIsDeleted()).ifPresent(existingRole::setIsDeleted);
        return existingRole;
    }
}
