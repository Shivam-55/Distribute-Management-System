package com.company.dms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.company.dms.entity.Role;

import java.util.Optional;

/**
 * Repository interface for managing role information.
 */
@Repository
public interface RoleInfoRepo extends JpaRepository<Role, Long> {
    /**
     * Retrieves the role by name.
     *
     * @param name the name of the role
     * @return an optional of role
     */
    Optional<Role> findByName(String name);
}
