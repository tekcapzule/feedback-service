package com.tekcapsule.feedback.domain.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.Select;
import com.tekcapsule.feedback.domain.model.Feedback;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
public class FeedbackRepositoryImpl implements FeedbackDynamoRepository {

    private DynamoDBMapper dynamo;

    @Autowired
    public FeedbackRepositoryImpl(DynamoDBMapper dynamo) {
        this.dynamo = dynamo;
    }


    @Override
    public List<Feedback> findAll() {

        return dynamo.scan(Feedback.class,new DynamoDBScanExpression());
    }

    @Override
    public Feedback findBy(String hashKey, String rangeKey) {
        return dynamo.load(Feedback.class, hashKey, rangeKey);
    }

    @Override
    public Feedback findBy(String hashKey) {
        return dynamo.load(Feedback.class, hashKey);
    }

    @Override
    public Feedback save(Feedback feedback) {
        dynamo.save(feedback);
        return feedback;
    }

    @Override
    public int getAllFeedbacksCount() {
        return dynamo.count(Feedback.class,new DynamoDBScanExpression().withSelect(Select.COUNT));
    }


}
