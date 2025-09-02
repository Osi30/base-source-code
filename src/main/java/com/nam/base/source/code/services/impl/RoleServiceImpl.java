package com.nam.base.source.code.services.impl;

import com.nam.base.source.code.dtos.request.RoleRequest;
import com.nam.base.source.code.dtos.response.RoleResponse;
import com.nam.base.source.code.entities.Role;
import com.nam.base.source.code.exceptions.exceptions.RoleException;
import com.nam.base.source.code.mappers.RoleMapper;
import com.nam.base.source.code.repositories.PermissionRepo;
import com.nam.base.source.code.repositories.RoleRepo;
import com.nam.base.source.code.services.RoleService;
import com.nam.base.source.code.utils.ValidationUtils;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final PermissionRepo permissionRepo;
    private final RoleRepo roleRepo;
    private final RoleMapper roleMapper;

    @Override
    public Role getRoleById(String id) {
        return roleRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Role not found with id " + id));
    }

    @Override
    public RoleResponse getRoleResponseById(String id) {
        return roleMapper.toRoleResponse(getRoleById(id));
    }

    @Override
    public Role getRoleByName(String name) {
        return roleRepo.findByRoleName(name)
                .orElseThrow(() -> new EntityNotFoundException("Role not found with name " + name));
    }

    @Override
    public List<RoleResponse> getAllRoles() {
        return roleRepo.findAllByIsDeletedFalse().stream()
                .map(roleMapper::toRoleResponse)
                .toList();
    }

    @Override
    public RoleResponse createRole(RoleRequest request) {
        if (ValidationUtils.isNullOrEmpty(request.getRoleName())) {
            throw new RoleException("Role name is required");
        }

        Role role = roleMapper.toRole(request);

        role.setPermissions(
                permissionRepo.findPermissionsByIdIn(request.getPermissions())
        );

        return roleMapper.toRoleResponse(roleRepo.save(role));
    }

    @Override
    public RoleResponse updateRole(RoleRequest role) {
        Role existingRole = getRoleById(role.getId());

        existingRole = roleMapper.updateRole(role, existingRole);

        if (ValidationUtils.isValidCollection(role.getPermissions())) {
            existingRole.setPermissions(
                    permissionRepo.findPermissionsByIdIn(role.getPermissions())
            );
        }

        return roleMapper.toRoleResponse(roleRepo.save(existingRole));
    }

    @Override
    public String deleteRole(String id) {
        Role role = getRoleById(id);
        role.setIsDeleted(true);
        roleRepo.save(role);
        return "Delete role successfully";
    }
}
