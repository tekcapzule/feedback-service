package com.tekcapsule.feedback.domain.repository;

import com.tekcapsule.core.domain.CrudRepository;
import com.tekcapsule.feedback.domain.model.Feedback;


public interface FeedbackDynamoRepository extends CrudRepository<Feedback, String> {

    public Feedback findBy(String hashKey, String rangeKey);

    }
