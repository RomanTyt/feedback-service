package com.tyutyakov.feedbackservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CommentToFeedbackGetDTO {

    private String commentID;

    private LocalDateTime dateTimeCreation;

    private String commentatorName;

    private String commentText;
}
