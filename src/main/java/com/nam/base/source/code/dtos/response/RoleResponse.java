package com.nam.base.source.code.dtos.response;

import com.nam.base.source.code.entities.Permission;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoleResponse {
    private String id;
    private String roleName;
    private Boolean isDeleted;
    private List<Permission> permissions;
}
