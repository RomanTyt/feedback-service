package com.tyutyakov.feedbackservice.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "organization_reply_table")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationReply  {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "organization_reply_id")
    private String organizationReplyID;

    @CreationTimestamp
    @Column(name = "date_time_creation")
    private LocalDateTime dateTimeCreation;

    @Column(name = "organization_reply_text")
    private String organizationReplyText;

    @OneToOne
    @JoinColumn(name = "feedback_id", referencedColumnName = "feedback_id")
    private Feedback feedback;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        OrganizationReply that = (OrganizationReply) o;
        return organizationReplyID != null && Objects.equals(organizationReplyID, that.organizationReplyID);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
