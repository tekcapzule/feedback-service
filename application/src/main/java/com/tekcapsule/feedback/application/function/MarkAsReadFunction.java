package com.tekcapsule.feedback.application.function;

import com.tekcapsule.core.domain.Origin;
import com.tekcapsule.core.utils.HeaderUtil;
import com.tekcapsule.feedback.application.config.AppConstants;
import com.tekcapsule.feedback.application.function.input.MarkAsReadInput;
import com.tekcapsule.feedback.application.mapper.InputOutputMapper;
import com.tekcapsule.feedback.domain.model.Feedback;ß
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
public class MarkAsReadFunction implements Function<Message<MarkAsReadInput>, Message<Void>> {
    private final FeedbackService feedbackService;

    public MarkAsReadFunction(final FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }


    @Override
    public Message<Void> apply(Message<MarkAsReadInput> disableInputMessage) {

        MarkAsReadInput markAsReadInput = disableInputMessage.getPayload();

        log.info(String.format("Entering disable mentor Function - Tenant Id:{0}, User Id:{1}", markAsReadInput.getTenantId(), markAsReadInput.getUserId()));

        Origin origin = HeaderUtil.buildOriginFromHeaders(disableInputMessage.getHeaders());

        DisableCommand disableCommand = InputOutputMapper.buildDisableCommandFromDisableInput.apply(markAsReadInput, origin);
        mentorService.disable(disableCommand);
        Map<String, Object> responseHeader = new HashMap();
        responseHeader.put(AppConstants.HTTP_STATUS_CODE_HEADER, HttpStatus.OK.value());

        return new GenericMessage( responseHeader);
    }
}
