package com.tyutyakov.feedbackservice.controller;

import com.tyutyakov.feedbackservice.model.dto.*;
import com.tyutyakov.feedbackservice.service.FeedbackService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/v1/feedbacks", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Отзывы")
public class FeedbackController {
    private final FeedbackService feedbackService;

    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @GetMapping("/{feedbackId}")
    @Operation(summary = "Найти отзыв по id")
    public FeedbackGetDTO findFeedbackById(@PathVariable String feedbackId){
        return feedbackService.findFeedbackById(feedbackId);
    }

    @PostMapping
    @Operation(summary = "Создать новый отзыв")
    @ResponseStatus(HttpStatus.CREATED)
    public String createNewFeedback(@RequestBody FeedbackCreateDTO feedbackCreateDTO) {
        return feedbackService.createNewFeedback(feedbackCreateDTO);
    }

    @PutMapping("/{feedbackId}")
    @Operation(summary = "Редактировать отзыв")
    public FeedbackGetDTO editFeedback(@PathVariable String feedbackId, @RequestBody FeedbackUpdateDTO feedbackUpdateDTO){
        return feedbackService.editFeedback(feedbackId, feedbackUpdateDTO);
    }

    @DeleteMapping("/{feedbackId}")
    @Operation(summary = "Удалить отзыв")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFeedbackById(@PathVariable String feedbackId){
        feedbackService.deleteFeedbackById(feedbackId);
    }

    @PostMapping("/{feedbackId}/comment")
    @Operation(summary = "Добавить комментарий к отзыву от другого пользователя")
    @ResponseStatus(HttpStatus.CREATED)
    public String addCommentToFeedback(@PathVariable String feedbackId, @RequestBody CommentToFeedbackCreateDTO commentToFeedbackCreateDTO){
        return feedbackService.addCommentToFeedback(feedbackId, commentToFeedbackCreateDTO);
    }

    @GetMapping("/{feedbackId}/comment")
    @Operation(summary = "Получить все комментарии к отзыву")
    public List<CommentToFeedbackGetDTO> getAllCommentToFeedback(@PathVariable String feedbackId){
        return feedbackService.getAllCommentToFeedback(feedbackId);
    }

    @PutMapping("/{feedbackId}/rate")
    @Operation(summary = "Оценить отзыв(лайк/дизлайк)")
    public String rateFeedback(@PathVariable String feedbackId, @RequestBody FeedbackRateDTO feedbackRateDTO){
        return feedbackService.rateFeedback(feedbackId, feedbackRateDTO);
    }

    @PostMapping("/{feedbackId}/reply")
    @Operation(summary = "Добавить ответ на отзыв от организации")
    @ResponseStatus(HttpStatus.CREATED)
    public String addOrganizationReply(@PathVariable String feedbackId, @RequestBody OrganizationReplyCreateDTO organizationReplyCreateDTO) {
            return feedbackService.addOrganizationReply(feedbackId, organizationReplyCreateDTO);
    }

    @GetMapping("/{feedbackId}/reply")
    @Operation(summary = "Получить ответ на отзыв от организации")
    public OrganizationReplyGetDTO getOrganizationReply(@PathVariable String feedbackId){
        return feedbackService.getOrganizationReply(feedbackId);
    }
}
