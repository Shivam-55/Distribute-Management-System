package com.company.complainservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.company.complainservice.entity.Complain;


/**
 * Repository interface for performing CRUD operations on the Complain entity.
 */
@Repository
public interface ComplainRepo extends JpaRepository<Complain,Long> {
}
