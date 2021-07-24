package com.tekcapsule.feedback.domain.service;

import com.tekcapsule.feedback.domain.command.CreateCommand;
import com.tekcapsule.feedback.domain.command.DisableCommand;
import com.tekcapsule.feedback.domain.command.UpdateCommand;
import com.tekcapsule.feedback.domain.model.Mentor;
import com.tekcapsule.feedback.domain.query.SearchItem;
import com.tekcapsule.feedback.domain.query.SearchQuery;

import java.util.List;

public interface FeedbackService {

    Mentor create(CreateCommand createCommand);

    Mentor update(UpdateCommand updateCommand);

    void disable(DisableCommand disableCommand);

    List<SearchItem> search(SearchQuery searchQuery);

    Mentor get(String tenantId, String userId);
}
