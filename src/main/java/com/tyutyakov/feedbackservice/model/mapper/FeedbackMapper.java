package com.tyutyakov.feedbackservice.model.mapper;

import com.tyutyakov.feedbackservice.model.dto.FeedbackCreateDTO;
import com.tyutyakov.feedbackservice.model.dto.FeedbackGetDTO;
import com.tyutyakov.feedbackservice.model.dto.FeedbackUpdateDTO;
import com.tyutyakov.feedbackservice.model.entity.Feedback;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface FeedbackMapper {

    Feedback feedbackCreateDTOMapToFeedback(FeedbackCreateDTO feedbackCreateDTO);

    FeedbackCreateDTO feedbackMapToFeedbackCreateDTO(Feedback feedback);

    void update(@MappingTarget Feedback feedback, FeedbackUpdateDTO feedbackUpdateDTO);

    FeedbackGetDTO feedbackMapToFeedbackGetDTO(Feedback feedback);
}
