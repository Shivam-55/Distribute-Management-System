package com.company.complainservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.company.complainservice.entity.Feedback;

import java.util.List;
import java.util.Optional;


/**
 * Repository interface for performing CRUD operations on the Feedback entity.
 */
@Repository
public interface FeedbackRepo extends JpaRepository<Feedback,Long> {
    Optional<List<Feedback>> findByFeedbackReceiverId(Long userId);
}
