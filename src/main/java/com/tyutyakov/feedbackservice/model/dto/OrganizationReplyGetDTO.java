package com.tyutyakov.feedbackservice.model.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class OrganizationReplyGetDTO {
    private String organizationReplyID;

    private LocalDateTime dateTimeCreation;

    private String organizationReplyText;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrganizationReplyGetDTO that = (OrganizationReplyGetDTO) o;

        return Objects.equals(organizationReplyID, that.organizationReplyID);
    }

    @Override
    public int hashCode() {
        return organizationReplyID != null ? organizationReplyID.hashCode() : 0;
    }
}
