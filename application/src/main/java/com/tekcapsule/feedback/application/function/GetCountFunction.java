package com.tekcapsule.feedback.application.function;

import com.tekcapsule.core.domain.EmptyFunctionInput;
import com.tekcapsule.core.utils.HeaderUtil;
import com.tekcapsule.core.utils.Outcome;
import com.tekcapsule.core.utils.Stage;
import com.tekcapsule.feedback.application.config.AppConfig;
import com.tekcapsule.feedback.domain.service.FeedbackService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
@Slf4j
public class GetCountFunction implements Function<Message<EmptyFunctionInput>, Message<Integer>> {

    private final FeedbackService feedbackService;
    private final AppConfig appConfig;

    public GetCountFunction(final FeedbackService feedbackService, final AppConfig appConfig) {
        this.feedbackService = feedbackService;
        this.appConfig = appConfig;
    }


    @Override
    public Message<Integer> apply(Message<EmptyFunctionInput> getAllCountMessage) {

        Map<String, Object> responseHeaders = new HashMap<>();
        int subscriptionsCount = 0;
        String stage = appConfig.getStage().toUpperCase();
        try {
            log.info("Entering get all feedbacks count Function");
            subscriptionsCount = feedbackService.getAllFeedbacksCount();
            responseHeaders = HeaderUtil.populateResponseHeaders(responseHeaders, Stage.valueOf(stage), Outcome.SUCCESS);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            responseHeaders = HeaderUtil.populateResponseHeaders(responseHeaders, Stage.valueOf(stage), Outcome.ERROR);
        }
        return new GenericMessage<>(subscriptionsCount, responseHeaders);
    }
}