package com.tekcapzule.feedback.application.function;

import com.tekcapzule.core.domain.Origin;
import com.tekcapzule.core.utils.HeaderUtil;
import com.tekcapzule.core.utils.Outcome;
import com.tekcapzule.core.utils.PayloadUtil;
import com.tekcapzule.core.utils.Stage;
import com.tekcapzule.feedback.application.config.AppConfig;
import com.tekcapzule.feedback.application.function.input.MarkAsReadInput;
import com.tekcapzule.feedback.application.mapper.InputOutputMapper;
import com.tekcapzule.feedback.domain.command.MarkAsReadCommand;
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
public class MarkAsReadFunction implements Function<Message<MarkAsReadInput>, Message<Void>> {
    private final FeedbackService feedbackService;

    private final AppConfig appConfig;

    public MarkAsReadFunction(final FeedbackService feedbackService, final AppConfig appConfig) {
        this.feedbackService = feedbackService;
        this.appConfig = appConfig;
    }

    @Override
    public Message<Void> apply(Message<MarkAsReadInput> markAsReadInputMessage) {
        Map<String, Object> responseHeaders = new HashMap<>();
        Map<String, Object> payload = new HashMap<>();
        String stage = appConfig.getStage().toUpperCase();
        try {
            MarkAsReadInput markAsReadInput = markAsReadInputMessage.getPayload();
            log.info(String.format("Entering mark as read feedback Function - Email Id:%S, Date %s", markAsReadInput.getEmailId(), markAsReadInput.getCommentedOn()));
            Origin origin = HeaderUtil.buildOriginFromHeaders(markAsReadInputMessage.getHeaders());
            MarkAsReadCommand markAsReadCommand = InputOutputMapper.buildMarkAsReadCommandFromMarkAsReadInput.apply(markAsReadInput, origin);
            feedbackService.markAsRead(markAsReadCommand);
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
