package com.tyutyakov.feedbackservice.model.mapper;

import com.tyutyakov.feedbackservice.model.dto.CommentToFeedbackCreateDTO;
import com.tyutyakov.feedbackservice.model.dto.CommentToFeedbackGetDTO;
import com.tyutyakov.feedbackservice.model.entity.CommentToFeedback;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface CommentToFeedbackMapper {
    CommentToFeedback createDtoMapToCommentToFeedback(CommentToFeedbackCreateDTO commentToFeedbackCreateDTO);

    CommentToFeedbackCreateDTO commentToFeedbackMapToCreateDTO(CommentToFeedback commentToFeedback);

    List<CommentToFeedbackGetDTO> convertListCommentToFeedbackToListCommentToFeedbackGetDTO(List<CommentToFeedback> commentToFeedback);

    CommentToFeedback getDtoMapToCommentToFeedback(CommentToFeedbackGetDTO commentToFeedbackGetDTO);

    CommentToFeedbackGetDTO commentToFeedbackMapToGetDTO(CommentToFeedback commentToFeedback);
}
