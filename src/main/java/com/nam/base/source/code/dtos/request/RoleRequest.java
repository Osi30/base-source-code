package com.nam.base.source.code.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleRequest {
    private String id;
    private String roleName;
    private Boolean isDeleted;
    private List<String> permissions;
}
