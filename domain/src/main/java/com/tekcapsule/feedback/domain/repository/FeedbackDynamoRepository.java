package com.tekcapsule.feedback.domain.repository;

import com.tekcapsule.feedback.domain.query.SearchItem;
import in.devstream.core.domain.CrudRepository;
import com.tekcapsule.feedback.domain.model.Mentor;

import java.util.List;

public interface FeedbackDynamoRepository extends CrudRepository<Mentor, String> {

    void disableById(String tenantId, String id);
    List<SearchItem> search(String tenantId);
}
