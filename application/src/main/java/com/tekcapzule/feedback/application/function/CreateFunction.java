package com.tekcapzule.feedback.application.function;

import com.tekcapzule.core.domain.Origin;
import com.tekcapzule.core.utils.HeaderUtil;
import com.tekcapzule.core.utils.Outcome;
import com.tekcapzule.core.utils.PayloadUtil;
import com.tekcapzule.core.utils.Stage;
import com.tekcapzule.feedback.application.config.AppConfig;
import com.tekcapzule.feedback.application.function.input.CreateInput;
import com.tekcapzule.feedback.application.mapper.InputOutputMapper;
import com.tekcapzule.feedback.domain.command.CreateCommand;
import com.tekcapzule.feedback.domain.service.FeedbackService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
@Slf4j
public class CreateFunction implements Function<Message<CreateInput>, Message<Void>> {

    private final FeedbackService feedbackService;

    private final AppConfig appConfig;

    public CreateFunction(final FeedbackService feedbackService, final AppConfig appConfig) {
        this.feedbackService = feedbackService;
        this.appConfig = appConfig;
    }

    @Override
    public Message<Void> apply(Message<CreateInput> createInputMessage) {
        Map<String, Object> responseHeaders = new HashMap<>();
        Map<String, Object> payload = new HashMap<>();
        String stage = appConfig.getStage().toUpperCase();

        try {

            CreateInput createInput = createInputMessage.getPayload();
            log.info(String.format("Entering create feedback function - Email Id:%s", createInput.getEmailId()));
            Origin origin = HeaderUtil.buildOriginFromHeaders(createInputMessage.getHeaders());
            CreateCommand createCommand = InputOutputMapper.buildCreateCommandFromCreateInput.apply(createInput, origin);
            feedbackService.create(createCommand);
            responseHeaders = HeaderUtil.populateResponseHeaders(responseHeaders, Stage.valueOf(stage), Outcome.SUCCESS);
            payload = PayloadUtil.composePayload(Outcome.SUCCESS);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            responseHeaders = HeaderUtil.populateResponseHeaders(responseHeaders, Stage.valueOf(stage), Outcome.ERROR);
            payload = PayloadUtil.composePayload(Outcome.ERROR);
        }
        return new GenericMessage(payload, responseHeaders);
    }
}