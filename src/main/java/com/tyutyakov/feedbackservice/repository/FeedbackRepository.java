package com.tyutyakov.feedbackservice.repository;

import com.tyutyakov.feedbackservice.model.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, String> {
    Optional<Feedback> findFeedbackByOrderId(String orderID);
    boolean existsFeedbackByOrderId(String orderID);
}