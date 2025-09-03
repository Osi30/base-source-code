package com.nam.base.source.code.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum DefaultPermission {
    MANAGE_PROFILE("Manage user own profile"),
    VIEW_PROFILE_LIST("View All Profiles in System"),
    BAN_PROFILE("Ban Specific Profile"),
    VIEW_ROLE_LIST("View All Roles in System"),
    MANAGE_ROLE("Manage roles in System"),
    BAN_PRODUCT("Ban Specific Product"),
    MANAGE_PRODUCT("Manage products in System"),
    ;

    private final String description;
}
