package com.nam.base.source.code.services.impl;

import com.nam.base.source.code.entities.Permission;
import com.nam.base.source.code.repositories.PermissionRepo;
import com.nam.base.source.code.services.PermissionService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService {
    private final PermissionRepo permissionRepo;

    @Override
    public Permission getPermissionById(String id) {
        return permissionRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Permission not found"));
    }

    @Cacheable(value = "permissions")
    @Override
    public List<Permission> getPermissions() {
        return permissionRepo.findAll();
    }

    @CacheEvict(value = {"permissions"}, allEntries = true)
    @Override
    public Permission createPermission(Permission permission) {
        return permissionRepo.save(permission);
    }

    @CacheEvict(value = {"permissions"}, allEntries = true)
    @Override
    public Permission updatePermission(Permission permission) {
        Permission oldPermission = getPermissionById(permission.getId());

        Optional.ofNullable(permission.getName()).ifPresent(oldPermission::setName);
        Optional.ofNullable(permission.getDescription()).ifPresent(oldPermission::setDescription);
        Optional.ofNullable(permission.getRoles()).ifPresent(oldPermission::setRoles);

        return permissionRepo.save(oldPermission);
    }

    @CacheEvict(value = {"permissions"}, allEntries = true)
    @Override
    public void deletePermission(String id) {
        permissionRepo.deleteById(id);
    }
}
