package com.tyutyakov.feedbackservice.controller;

import com.tyutyakov.feedbackservice.model.dto.FeedbackRateDTO;
import com.tyutyakov.feedbackservice.model.entity.CommentToFeedback;
import com.tyutyakov.feedbackservice.model.entity.Feedback;
import com.tyutyakov.feedbackservice.model.entity.ResponseOrganization;
import com.tyutyakov.feedbackservice.service.FeedbackService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/v1/feedback", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Отзывы")
public class FeedbackController {
    private final FeedbackService feedbackService;

    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Найти отзыв по id")
    public Feedback findFeedbackById(@PathVariable String id){
        return feedbackService.findFeedbackById(id);
    }

    @PostMapping
    @Operation(summary = "Создать новый отзыв")
    public String createNewFeedback(@RequestBody Feedback feedback) {
        return feedbackService.createNewFeedback(feedback);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Редактировать отзыв")
    public String editFeedback(@PathVariable String id, @RequestBody Feedback updatedFeedback){
        return feedbackService.editFeedback(id, updatedFeedback);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить отзыв")
    public String deleteFeedbackById(@PathVariable String id){
        return feedbackService.deleteFeedbackById(id);
    }

    @PostMapping("/CommentToFeedback/{id}")
    @Operation(summary = "Добавить комментарий к отзыву от другого пользователя")
    public String addCommentToFeedback(@PathVariable String id, @RequestBody CommentToFeedback commentToFeedback){
        return feedbackService.addCommentToFeedback(id, commentToFeedback);
    }

    @PutMapping("/RateFeedback/{id}")
    @Operation(summary = "Оценить отзыв(лайк/дизлайк)")
    public String rateFeedback(@PathVariable String id, @RequestBody FeedbackRateDTO feedbackRateDTO){
        return feedbackService.rateFeedback(id, feedbackRateDTO);
    }

    @PostMapping("/ResponseOrganization")
    @Operation(summary = "Добавить ответ на отзыв от организации")
    public String addResponseOrganization(@RequestBody ResponseOrganization responseOrganization){
        return feedbackService.addResponseOrganization(responseOrganization);
    }
}
