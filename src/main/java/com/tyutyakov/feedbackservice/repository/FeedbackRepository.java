package com.tyutyakov.feedbackservice.repository;

import com.tyutyakov.feedbackservice.model.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, String> {
}