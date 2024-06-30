package com.company.dms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.company.dms.customenum.IsApproved;
import com.company.dms.entity.Role;
import com.company.dms.entity.User;

import java.util.List;
import java.util.Optional;
/**
 * Repository interface for managing user information.
 */
@Repository
public interface UserInfoRepo extends JpaRepository<User, Long> {
    /**
     * Finds a user by their ID.
     * @param userId The ID of the user to find.
     * @return An Optional containing the user, or empty if not found.
     */
    Optional<User> findByUserId(Long userId);

    /**
     * Finds a user by their area and role.
     * @param area The area of the user.
     * @param role The role of the user.
     * @return An Optional containing the user, or empty if not found.
     */
    Optional<User> findByAreaAndRole(String area, Role role);

    /**
     * Finds a user by their email.
     * @param email The email of the user.
     * @return An Optional containing the user, or empty if not found.
     */
    Optional<User> findByEmail(String email);

    /**
     * Finds a user by their mobile number.
     * @param mobile The mobile number of the user.
     * @return An Optional containing the user, or empty if not found.
     */
    Optional<User> findByMobile(Long mobile);

    /**
     * Finds users by their area, role, and whether they are active.
     * @param area The area of the users.
     * @param role The role of the users.
     * @param isActive Whether the users are active or not.
     * @return A list of users matching the criteria.
     */
    Optional<List<User>> findByAreaAndRoleAndIsActive(String area, Role role, boolean isActive);

    /**
     * Finds users by their role, whether they are active, and their approval status.
     * @param role The role of the users.
     * @param isActive Whether the users are active or not.
     * @param isApproved The approval status of the users.
     * @return A list of users matching the criteria.
     */
    Optional<List<User>> findByRoleAndIsActiveAndIsApproved(Role role, boolean isActive, IsApproved isApproved);

    /**
     * Finds users by whether they are active and their approval status.
     * @param isActive Whether the users are active or not.
     * @param isApproved The approval status of the users.
     * @return A list of users matching the criteria.
     */
    Optional<List<User>> findByIsActiveAndIsApproved(boolean isActive, IsApproved isApproved);

    /**
     * Finds users by their area, role, whether they are active, and their approval status.
     * @param area The area of the users.
     * @param role The role of the users.
     * @param isActive Whether the users are active or not.
     * @param isApproved The approval status of the users.
     * @return An Optional containing the user, or empty if not found.
     */
    Optional<User> findByAreaAndRoleAndIsActiveAndIsApproved(String area, Role role, boolean isActive, IsApproved isApproved);

    /**
     * Finds users by their role, area, whether they are active, and their approval status.
     * @param role The role of the users.
     * @param area The area of the users.
     * @param isActive Whether the users are active or not.
     * @param isApproved The approval status of the users.
     * @return A list of users matching the criteria.
     */
    Optional<List<User>> findByRoleAndAreaAndIsActiveAndIsApproved(Role role, String area, boolean isActive, IsApproved isApproved);
}