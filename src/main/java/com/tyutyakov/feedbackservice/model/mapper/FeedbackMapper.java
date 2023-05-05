package com.tyutyakov.feedbackservice.model.mapper;

import com.tyutyakov.feedbackservice.model.dto.FeedbackCreateDTO;
import com.tyutyakov.feedbackservice.model.dto.FeedbackGetDTO;
import com.tyutyakov.feedbackservice.model.dto.FeedbackInfo;
import com.tyutyakov.feedbackservice.model.dto.FeedbackUpdateDTO;
import com.tyutyakov.feedbackservice.model.entity.Feedback;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.time.LocalDate;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface FeedbackMapper {

    Feedback feedbackCreateDTOMapToFeedback(FeedbackCreateDTO feedbackCreateDTO);

    FeedbackCreateDTO feedbackMapToFeedbackCreateDTO(Feedback feedback);

    void update(@MappingTarget Feedback feedback, FeedbackUpdateDTO feedbackUpdateDTO);

    FeedbackGetDTO feedbackMapToFeedbackGetDTO(Feedback feedback);

    default FeedbackInfo FeedbackMapToFeedbackInfo(Feedback feedback){
        int rating = Math.abs(feedback.getFeedbackRatingLike() - feedback.getFeedbackRatingDislike());
        int commentCount = feedback.getComments().size();
        LocalDate date = feedback.getDateTimeCreation().toLocalDate();
        return new FeedbackInfo(feedback.getOrderId(), feedback.getFeedbackAuthorName(), feedback.getFeedbackText(), rating, commentCount, date);
    }
}
