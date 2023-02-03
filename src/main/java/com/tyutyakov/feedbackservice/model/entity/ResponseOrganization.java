package com.tyutyakov.feedbackservice.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;

@Entity
@Table(name = "response_organization_table")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ResponseOrganization {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "response_organization_id")
    private String responseOrganizationID;
    @Column(name = "feedback_id")
    private String feedbackID;
    @Column(name = "date_time_creation")
    private LocalDateTime dateTimeCreation;
    @Column(name = "response_organization_text")
    private String responseOrganizationText;
}
