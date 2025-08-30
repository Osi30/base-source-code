package com.nam.base.source.code.repositories;

import com.nam.base.source.code.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoleRepo extends JpaRepository<Role, String> {
    Optional<Role> findByRoleName(String name);
    List<Role> findAllByIsDeletedFalse();
}
