package com.tekcapsule.feedback.application.function;

import com.tekcapsule.feedback.application.config.AppConstants;
import com.tekcapsule.feedback.domain.model.Feedback;
import com.tekcapsule.feedback.domain.service.FeedbackService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;


@Component
@Slf4j
public class SearchFunction implements Function<Message<SearchInput>, Message<List<SearchItem>>> {

    private final FeedbackService feedbackService;

    public SearchFunction(final FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }


    @Override
    public Message<List<SearchItem>> apply(Message<SearchInput> searchInputMessage) {
        SearchInput searchInput = searchInputMessage.getPayload();

        log.info(String.format("Entering search mentor Function - Tenant Id:{0}", searchInput.getTenantId()));

        List<SearchItem> searchItems = mentorService.search(SearchQuery.builder().tenantId(searchInput.getTenantId()).build());
        Map<String, Object> responseHeader = new HashMap();
        responseHeader.put(AppConstants.HTTP_STATUS_CODE_HEADER, HttpStatus.OK.value());

        return new GenericMessage(searchItems, responseHeader);
    }
}