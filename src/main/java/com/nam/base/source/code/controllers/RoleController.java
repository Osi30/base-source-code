package com.nam.base.source.code.controllers;

import com.nam.base.source.code.dtos.BaseResponse;
import com.nam.base.source.code.dtos.request.RoleRequest;
import com.nam.base.source.code.dtos.response.RoleResponse;
import com.nam.base.source.code.services.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/role")
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;

    @PreAuthorize("hasAuthority('VIEW_ROLE_LIST')")
    @GetMapping("/list")
    public ResponseEntity<BaseResponse> getRoles() {
        List<RoleResponse> response = roleService.getAllRoles();

        BaseResponse baseResponse = BaseResponse.builder()
                .code(HttpStatus.OK.value())
                .message("Get roles successfully!")
                .data(response)
                .build();
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @GetMapping("/{roleId}")
    public ResponseEntity<BaseResponse> getRoleById(
            @PathVariable String roleId
    ) {
        RoleResponse response = roleService.getRoleResponseById(roleId);

        BaseResponse baseResponse = BaseResponse.builder()
                .code(HttpStatus.OK.value())
                .message("Get role successfully!")
                .data(response)
                .build();
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('MANAGE_ROLE')")
    @PostMapping()
    public ResponseEntity<BaseResponse> createRole(
            @RequestBody RoleRequest request
    ) {
        RoleResponse response = roleService.createRole(request);

        BaseResponse baseResponse = BaseResponse.builder()
                .code(HttpStatus.OK.value())
                .message("Create role successfully!")
                .data(response)
                .build();
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('MANAGE_ROLE')")
    @PutMapping()
    public ResponseEntity<BaseResponse> updateRole(
            @RequestBody RoleRequest request
    ) {
        RoleResponse response = roleService.updateRole(request);

        BaseResponse baseResponse = BaseResponse.builder()
                .code(HttpStatus.OK.value())
                .message("Update role successfully!")
                .data(response)
                .build();
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('MANAGE_ROLE')")
    @DeleteMapping("/{roleId}")
    public ResponseEntity<BaseResponse> deleteRole(
            @PathVariable String roleId
    ) {
        String response = roleService.deleteRole(roleId);

        BaseResponse baseResponse = BaseResponse.builder()
                .code(HttpStatus.OK.value())
                .message("Delete role successfully!")
                .data(response)
                .build();
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }
}
