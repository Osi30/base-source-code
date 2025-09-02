package com.nam.base.source.code.repositories;

import com.nam.base.source.code.entities.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PermissionRepo extends JpaRepository<Permission, String> {
    List<Permission> findPermissionsByIdIn(List<String> ids);
}
