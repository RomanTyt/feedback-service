package com.tyutyakov.feedbackservice.repository;

import com.tyutyakov.feedbackservice.model.entity.OrganizationReply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationReplyRepository extends JpaRepository<OrganizationReply, String> {
    boolean existsOrganizationReplyByFeedback_FeedbackID (String feedbackId);
}