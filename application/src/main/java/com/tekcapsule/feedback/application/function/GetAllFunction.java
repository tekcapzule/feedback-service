package com.tekcapsule.feedback.application.function;

import com.tekcapsule.core.domain.EmptyFunctionInput;
import com.tekcapsule.core.utils.HeaderUtil;
import com.tekcapsule.core.utils.Outcome;
import com.tekcapsule.core.utils.Stage;
import com.tekcapsule.feedback.application.config.AppConfig;
import com.tekcapsule.feedback.domain.model.Feedback;
import com.tekcapsule.feedback.domain.service.FeedbackService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;


@Component
@Slf4j
public class GetAllFunction implements Function<Message<EmptyFunctionInput>, Message<List<Feedback>>> {

    private final FeedbackService feedbackService;

    private final AppConfig appConfig;

    public GetAllFunction(final FeedbackService feedbackService, final AppConfig appConfig) {
        this.feedbackService = feedbackService;
        this.appConfig = appConfig;
    }

    @Override
    public Message<List<Feedback>> apply(Message<EmptyFunctionInput> findAllInputMessage) {
        Map<String, Object> responseHeaders = new HashMap<>();
        Map<String, Object> payload = new HashMap<>();
        List<Feedback> feedbacks = new ArrayList<>();
        String stage = appConfig.getStage().toUpperCase();
        try {
            log.info("Entering get all feedback Function");
            feedbacks = feedbackService.findAll();
            Map<String, Object> responseHeader = new HashMap<>();
            responseHeaders = HeaderUtil.populateResponseHeaders(responseHeaders, Stage.valueOf(stage), Outcome.NOT_FOUND);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            responseHeaders = HeaderUtil.populateResponseHeaders(responseHeaders, Stage.valueOf(stage), Outcome.ERROR);
        }
        return new GenericMessage(feedbacks, responseHeaders);
    }
}