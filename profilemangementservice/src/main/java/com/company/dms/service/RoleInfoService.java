package com.company.dms.service;

import com.company.dms.entity.Role;

/**
 * Interface for RoleInfoService.
 */
public interface RoleInfoService {
    /**
     * Method to find a role by its name.
     *
     * @param roleName The name of the role to find.
     * @return Role object representing the role found.
     */
    Role findByRoleName(String roleName);
    void roleEntry();
}
