package com.tyutyakov.feedbackservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CommentToFeedbackCreateDTO {

    private String commentatorName;

    private String commentText;
}
