package com.tyutyakov.feedbackservice.controller;

import com.tyutyakov.feedbackservice.service.FeedbackService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/v1/feedback", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Отзывы")
public class FeedbackController {
    private final FeedbackService feedbackService;

    @Autowired
    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }


    @GetMapping("/{id}")
    @Operation(summary = "Найти отзыв по id")
    public String findFeedbackById(@PathVariable String id){
        return "123";
    }

    @PostMapping
    @Operation(summary = "Создать новый отзыв")
    public String createNewFeedback() {
        return "123";
    }

    @PutMapping
    @Operation(summary = "Редактировать отзыв")
    public String editFeedback(){
        return "123";
    }

    @DeleteMapping
    @Operation(summary = "Удалить отзыв")
    public String deleteFeedback(){
        return "123";
    }

    @PostMapping("/123")
    @Operation(summary = "Добавить комментарий к отзыву от другого пользователя")
    public String addCommentToFeedback(){
        return "123";
    }

    @PutMapping("/123")
    @Operation(summary = "Оценить отзыв(лайк/дизлайк)")
    public String rateFeedback(){
        return "123";
    }

    @PostMapping("/1234")
    @Operation(summary = "Добавить ответ на отзыв от организации")
    public String addResponseOrganization(){
        return "123";
    }

}
