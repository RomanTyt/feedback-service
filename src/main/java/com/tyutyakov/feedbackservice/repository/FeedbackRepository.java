package com.tyutyakov.feedbackservice.repository;

import com.tyutyakov.feedbackservice.model.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, String> {
    Optional<Feedback> findFeedbackByOrderID(String orderID);

    default boolean checkFeedbackContainsInDB(String orderID){
        try {
            findFeedbackByOrderID(orderID);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}