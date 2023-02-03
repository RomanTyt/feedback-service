package com.tyutyakov.feedbackservice.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;

@Entity
@Table(name = "comment_to_feedback_table")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class CommentToFeedback {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "comment_id")
    private String commentID;
    @Column(name = "feedback_id")
    private String feedbackID;
    @Column(name = "commentator_name")
    private String commentatorName;
    @Column(name = "date_time_creation")
    private LocalDateTime dateTimeCreation;
    @Column(name = "comment_text")
    private String commentText;

}

