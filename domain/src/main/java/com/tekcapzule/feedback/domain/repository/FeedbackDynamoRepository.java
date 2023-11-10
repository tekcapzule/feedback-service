package com.tekcapzule.feedback.domain.repository;

import com.tekcapzule.core.domain.CrudRepository;
import com.tekcapzule.feedback.domain.model.Feedback;


public interface FeedbackDynamoRepository extends CrudRepository<Feedback, String> {

    public Feedback findBy(String hashKey, String rangeKey);

    int getAllFeedbacksCount();

    }
