package com.tyutyakov.feedbackservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CommentToFeedbackGetDTO {

    private String commentId;

    private LocalDateTime dateTimeCreation;

    private String commentatorName;

    private String commentText;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CommentToFeedbackGetDTO that = (CommentToFeedbackGetDTO) o;

        return Objects.equals(commentId, that.commentId);
    }

    @Override
    public int hashCode() {
        return commentId != null ? commentId.hashCode() : 0;
    }
}
