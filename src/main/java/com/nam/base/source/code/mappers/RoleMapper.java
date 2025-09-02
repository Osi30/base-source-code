package com.nam.base.source.code.mappers;

import com.nam.base.source.code.dtos.request.RoleRequest;
import com.nam.base.source.code.dtos.response.RoleResponse;
import com.nam.base.source.code.entities.Role;

public interface RoleMapper {
    Role toRole(RoleRequest roleRequest);
    RoleResponse toRoleResponse(Role role);
    Role updateRole(RoleRequest roleRequest, Role existingRole);
}
