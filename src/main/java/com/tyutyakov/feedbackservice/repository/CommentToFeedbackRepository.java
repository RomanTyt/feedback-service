package com.tyutyakov.feedbackservice.repository;

import com.tyutyakov.feedbackservice.model.entity.CommentToFeedback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentToFeedbackRepository extends JpaRepository<CommentToFeedback, String> {
}
