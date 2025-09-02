package com.nam.base.source.code.services;

import com.nam.base.source.code.entities.Permission;

import java.util.List;

public interface PermissionService {
    Permission getPermissionById(String id);
    List<Permission> getPermissions();
    Permission createPermission(Permission permission);
    Permission updatePermission(Permission permission);
    void deletePermission(String id);
}
