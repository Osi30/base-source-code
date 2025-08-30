package com.nam.base.source.code.services;

import com.nam.base.source.code.dtos.request.RoleRequest;
import com.nam.base.source.code.dtos.response.RoleResponse;
import com.nam.base.source.code.entities.Role;

import java.util.List;

public interface RoleService {
    Role getRoleById(String id);
    RoleResponse getRoleResponseById(String id);
    Role getRoleByName(String name);
    List<RoleResponse> getAllRoles();
    RoleResponse createRole(RoleRequest role);
    RoleResponse updateRole(RoleRequest role);
    String deleteRole(String id);

}
