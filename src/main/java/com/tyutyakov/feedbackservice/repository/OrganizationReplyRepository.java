package com.tyutyakov.feedbackservice.repository;

import com.tyutyakov.feedbackservice.model.entity.OrganizationReply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationReplyRepository extends JpaRepository<OrganizationReply, String> {
    /**
     * Проверка наличия ответа организации на отзыв в БД
     *
     * @param feedbackId  id отзыва
     * @return True или False
     */
    boolean existsOrganizationReplyByFeedback_feedbackId(String feedbackId);
}