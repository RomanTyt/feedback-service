package com.tyutyakov.feedbackservice.repository;

import com.tyutyakov.feedbackservice.model.entity.ResponseOrganization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResponseOrganizationRepository extends JpaRepository<ResponseOrganization, String> {
}
