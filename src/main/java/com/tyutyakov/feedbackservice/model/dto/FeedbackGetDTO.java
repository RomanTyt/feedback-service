package com.tyutyakov.feedbackservice.model.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FeedbackGetDTO {

    private LocalDateTime dateTimeCreation;

    private LocalDateTime dateTimeUpdate;

    private String feedbackAuthorName;

    private String orderID;

    private String feedbackText;

    private String advantagesOfTheProduct;

    private String disadvantagesOfTheProduct;

    private int deliverySpeedAssessment;

    private int productQualityAssessment;

    private int feedbackRatingLike;

    private int feedbackRatingDislike;

}
