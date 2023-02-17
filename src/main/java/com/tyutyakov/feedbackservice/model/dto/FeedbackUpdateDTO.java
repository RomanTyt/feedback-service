package com.tyutyakov.feedbackservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class FeedbackUpdateDTO {

    private String feedbackText;

    private String advantagesOfTheProduct;

    private String disadvantagesOfTheProduct;

    private int deliverySpeedAssessment;

    private int productQualityAssessment;
}
