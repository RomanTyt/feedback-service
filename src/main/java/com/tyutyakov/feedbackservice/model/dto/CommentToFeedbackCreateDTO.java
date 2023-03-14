package com.tyutyakov.feedbackservice.model.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
public class CommentToFeedbackCreateDTO {

    private String commentatorName;

    private String commentText;
}
