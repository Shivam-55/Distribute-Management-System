package com.company.dms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.company.dms.entity.ForgotPassword;

import java.util.Optional;

/**
 * Repository interface for managing forgot password requests.
 */
@Repository
public interface ForgotPasswordRepo extends JpaRepository<ForgotPassword, Long> {
    /**
     * Retrieves the forgot password request by email.
     *
     * @param email the email address
     * @return an optional of forgot password request
     */
    Optional<ForgotPassword> findByEmail(String email);

    /**
     * Deletes the forgot password request by email.
     *
     * @param email the email address
     */
    void deleteByEmail(String email);
}