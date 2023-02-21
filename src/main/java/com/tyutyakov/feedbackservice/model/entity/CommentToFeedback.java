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
@Table(name = "comment_to_feedback_table")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentToFeedback {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "comment_id")
    private String commentID;

    @Column(name = "commentator_name")
    private String commentatorName;

    @CreationTimestamp
    @Column(name = "date_time_creation")
    private LocalDateTime dateTimeCreation;

    @Column(name = "comment_text")
    private String commentText;

    @ManyToOne
    @JoinColumn(name = "feedback_id", referencedColumnName = "feedback_id")
    private Feedback feedback;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        CommentToFeedback that = (CommentToFeedback) o;
        return commentID != null && Objects.equals(commentID, that.commentID);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}