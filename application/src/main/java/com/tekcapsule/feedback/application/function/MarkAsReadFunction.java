package com.tekcapsule.feedback.application.function;

import com.tekcapsule.core.domain.Origin;
import com.tekcapsule.core.utils.HeaderUtil;
import com.tekcapsule.feedback.application.config.AppConstants;
import com.tekcapsule.feedback.application.function.input.MarkAsReadInput;
import com.tekcapsule.feedback.application.mapper.InputOutputMapper;
import com.tekcapsule.feedback.domain.command.MarkAsReadCommand;
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
    public Message<Void> apply(Message<MarkAsReadInput> markAsReadInputMessage) {

        MarkAsReadInput markAsReadInput = markAsReadInputMessage.getPayload();

        log.info(String.format("Entering mark as read feedback Function - Email Id:{0}", markAsReadInput.getId()));

        Origin origin = HeaderUtil.buildOriginFromHeaders(markAsReadInputMessage.getHeaders());

        MarkAsReadCommand markAsReadCommand = InputOutputMapper.buildMarkAsReadCommandFromMarkAsReadInput.apply(markAsReadInput, origin);
        feedbackService.markAsRead(markAsReadCommand);
        Map<String, Object> responseHeader = new HashMap();
        responseHeader.put(AppConstants.HTTP_STATUS_CODE_HEADER, HttpStatus.OK.value());

        return new GenericMessage( responseHeader);
    }
}
