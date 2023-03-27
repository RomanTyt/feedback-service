package com.tyutyakov.feedbackservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tyutyakov.feedbackservice.model.dto.*;
import com.tyutyakov.feedbackservice.model.entity.CommentToFeedback;
import com.tyutyakov.feedbackservice.model.entity.Feedback;
import com.tyutyakov.feedbackservice.model.entity.OrganizationReply;
import com.tyutyakov.feedbackservice.service.FeedbackService;
import net.minidev.json.JSONArray;
import net.minidev.json.parser.JSONParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FeedbackController.class)
class FeedbackControllerTest {
    @MockBean
    private FeedbackService sut;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    JSONParser jsonParser= new JSONParser(JSONParser.DEFAULT_PERMISSIVE_MODE);

    private final List<CommentToFeedback> commentToFeedbackList = new ArrayList<>();
    private final List<CommentToFeedbackGetDTO> commentToFeedbackGetDTOList = new ArrayList<>();
    private final String feedbackId = "1c5afdce-f34e-445a-9d25-d548b5ad515c";
    private final LocalDateTime localDateTime = LocalDateTime.parse("2023-03-01T22:36:56");//LocalDateTime.now();

    private final CommentToFeedback commentToFeedback = new CommentToFeedback("e8f191f9-c133-4901-a44c-99200ec6202a",
            "Вова", localDateTime, "текст", null);
    private final CommentToFeedbackGetDTO commentToFeedbackGetDTO = new CommentToFeedbackGetDTO("e8f191f9-c133-4901-a44c-99200ec6202a",
             localDateTime, "Вова", "Текст");
    private final CommentToFeedbackCreateDTO commentToFeedbackCreateDTO = new CommentToFeedbackCreateDTO("Вова", "текст");

    private final OrganizationReply organizationReply = new OrganizationReply("1212121-222222-333333-444444", localDateTime, "текст", null);
    private final OrganizationReplyCreateDTO organizationReplyCreateDTO = new OrganizationReplyCreateDTO("текст ответа");
    private final OrganizationReplyGetDTO organizationReplyGetDTO = new OrganizationReplyGetDTO("1212121-222222-333333-444444", localDateTime, "текст");

    private final FeedbackRateDTO feedbackRateDTO = new FeedbackRateDTO(true);
    private final FeedbackCreateDTO feedbackCreateDTO = new FeedbackCreateDTO("Вася", "1111111-222222-333333-444444",
            "Всё хорошо", "Есть", "Нет", 4, 5);
    private final Feedback feedback = new Feedback("1c5afdce-f34e-445a-9d25-d548b5ad515c", localDateTime, localDateTime, "Вася",
            "1111111-222222-333333-444444", "Всё хорошо", "Есть", "Нет",
            4, 5, 15, 40, commentToFeedbackList, organizationReply);
    private final FeedbackGetDTO feedbackGetDTO = new FeedbackGetDTO(localDateTime, localDateTime, "Вася",
            "1111111-222222-333333-444444", "Всё хорошо", "Есть", "Нет",
            4, 5, 15, 40);
    private final FeedbackUpdateDTO feedbackUpdateDTO = new FeedbackUpdateDTO("Всё плохо", "Нет", "Есть",
            2, 1);

    @BeforeEach
    void setUp() {
        commentToFeedbackGetDTOList.add(commentToFeedbackGetDTO);
        commentToFeedbackGetDTOList.add(commentToFeedbackGetDTO);
    }

    @Test
    void findFeedbackById() throws Exception {
        Mockito.doReturn(feedbackGetDTO).when(sut).findFeedbackById(feedbackId);
        mockMvc.perform(get("/api/v1/feedbacks/{feedbackId}", feedbackId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderID").value(feedbackGetDTO.getOrderID()));
    }

    @Test
    void createNewFeedback() throws Exception {
        Mockito.doReturn(feedbackId).when(sut).createNewFeedback(feedbackCreateDTO);
        mockMvc.perform(post("/api/v1/feedbacks")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content("""
                                {
                                  "feedbackAuthorName": "Вася",
                                  "orderID": "1111111-222222-333333-444444",
                                  "feedbackText": "Всё хорошо",
                                  "advantagesOfTheProduct": "Есть",
                                  "disadvantagesOfTheProduct": "Нет",
                                  "deliverySpeedAssessment": 4,
                                  "productQualityAssessment": 5
                                }
                                """))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$").value(feedbackId));
    }

    @Test
    void editFeedback() throws Exception {
        Mockito.doReturn(feedbackGetDTO).when(sut).editFeedback(feedbackId, feedbackUpdateDTO);
        mockMvc.perform(put("/api/v1/feedbacks/{feedbackId}", feedbackId)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content("""
                                {
                                  "feedbackText": "Всё плохо",
                                  "advantagesOfTheProduct": "Нет",
                                  "disadvantagesOfTheProduct": "Есть",
                                  "deliverySpeedAssessment": 2,
                                  "productQualityAssessment": 1
                                }
                                """))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderID").value(feedbackGetDTO.getOrderID()));
    }

    @Test
    void deleteFeedbackById() throws Exception {
        mockMvc.perform(delete("/api/v1/feedbacks/{feedbackId}", feedbackId))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    void addCommentToFeedback() throws Exception {
        Mockito.doReturn(commentToFeedback.getCommentID()).when(sut).addCommentToFeedback(feedbackId, commentToFeedbackCreateDTO);
        mockMvc.perform(post("/api/v1/feedbacks/{feedbackId}/comment", feedbackId)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content("""
                                {
                                  "commentatorName": "Вова",
                                  "commentText": "текст"
                                }
                                """))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$").value(commentToFeedback.getCommentID()));
    }

    @Test
    void getAllCommentToFeedback() throws Exception {
        Mockito.doReturn(commentToFeedbackGetDTOList).when(sut).getAllCommentToFeedback(feedbackId);
        JSONArray listJson = (JSONArray) jsonParser.parse(objectMapper.writeValueAsString(commentToFeedbackGetDTOList));
        mockMvc.perform(get("/api/v1/feedbacks/{feedbackId}/comment", feedbackId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(listJson));
    }

    @Test
    void rateFeedback() throws Exception {
        Mockito.doReturn("Like - " + feedback.getFeedbackRatingLike() + ", Dislike - " + feedback.getFeedbackRatingDislike())
                .when(sut).rateFeedback(feedbackId, feedbackRateDTO);
        mockMvc.perform(put("/api/v1/feedbacks/{feedbackId}/rate", feedbackId)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content("""
                                {
                                  "rate": true
                                }
                                """))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("Like - " + feedback.getFeedbackRatingLike() + ", Dislike - " + feedback.getFeedbackRatingDislike()));
    }

    @Test
    void addOrganizationReply() throws Exception {
        Mockito.doReturn(organizationReply.getOrganizationReplyID()).when(sut).addOrganizationReply(feedbackId, organizationReplyCreateDTO);
        mockMvc.perform(post("/api/v1/feedbacks/{feedbackId}/reply", feedbackId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content("""
                                    {
                                      "organizationReplyText": "текст ответа"
                                    }
                                    """))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$").value(organizationReply.getOrganizationReplyID()));
    }

    @Test
    void getOrganizationReply() throws Exception {
        Mockito.doReturn(organizationReplyGetDTO).when(sut).getOrganizationReply(feedbackId);
        mockMvc.perform(get("/api/v1/feedbacks/{feedbackId}/reply", feedbackId)
                .content(feedbackId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.organizationReplyID").value(organizationReplyGetDTO.getOrganizationReplyID()));
    }
}