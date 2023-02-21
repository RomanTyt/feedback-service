package com.tyutyakov.feedbackservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class OrganizationReplyGetDTO {
    private String organizationReplyID;

    private LocalDateTime dateTimeCreation;

    private String organizationReplyText;
}
