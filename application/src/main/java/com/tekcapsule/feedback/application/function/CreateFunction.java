package com.tekcapsule.feedback.application.function;

import com.tekcapsule.core.domain.Origin;
import com.tekcapsule.core.utils.HeaderUtil;
import com.tekcapsule.feedback.application.config.AppConstants;
import com.tekcapsule.feedback.application.function.input.CreateInput;
import com.tekcapsule.feedback.application.mapper.InputOutputMapper;
import com.tekcapsule.feedback.domain.command.CreateCommand;
import com.tekcapsule.feedback.domain.model.Feedback;
import com.tekcapsule.feedback.domain.service.FeedbackService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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

    public CreateFunction(final FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }


    @Override
    public Message<Void> apply(Message<CreateInput> createInputMessage) {

        CreateInput createInput = createInputMessage.getPayload();

        log.info(String.format("Entering create feedback function - Email Id:%S", createInput.getEmailId()));

        Origin origin = HeaderUtil.buildOriginFromHeaders(createInputMessage.getHeaders());

        CreateCommand createCommand = InputOutputMapper.buildCreateCommandFromCreateInput.apply(createInput, origin);
        feedbackService.create(createCommand);
        Map<String, Object> responseHeader = new HashMap<>();
        responseHeader.put(AppConstants.HTTP_STATUS_CODE_HEADER, HttpStatus.OK.value());

        return new GenericMessage( responseHeader);
    }
}