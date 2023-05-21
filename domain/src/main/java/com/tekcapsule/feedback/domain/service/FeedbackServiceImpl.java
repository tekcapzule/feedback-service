package com.tekcapsule.feedback.domain.service;

import com.tekcapsule.feedback.domain.command.CreateCommand;
import com.tekcapsule.feedback.domain.command.MarkAsReadCommand;
import com.tekcapsule.feedback.domain.model.Feedback;
import com.tekcapsule.feedback.domain.repository.FeedbackDynamoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Slf4j
@Service
public class FeedbackServiceImpl implements FeedbackService {
    private FeedbackDynamoRepository feedbackDynamoRepository;

    @Autowired
    public FeedbackServiceImpl(FeedbackDynamoRepository feedbackDynamoRepository) {
        this.feedbackDynamoRepository = feedbackDynamoRepository;
    }

    @Override
    public void create(CreateCommand createCommand) {

        log.info(String.format("Entering create feedback service - Email Id:%s", createCommand.getEmailId()));

        Feedback feedback = Feedback.builder()
                .emailId(createCommand.getEmailId())
                .firstName(createCommand.getFirstName())
                .lastName(createCommand.getLastName())
                .subject(createCommand.getSubject())
                .description(createCommand.getDescription())
                .commentedOn(Instant.now().toString())
                .read(false)
                .build();

        feedback.setAddedOn(createCommand.getExecOn());
        feedback.setUpdatedOn(createCommand.getExecOn());
        feedback.setAddedBy(createCommand.getExecBy().getUserId());
        feedback.setUpdatedBy(createCommand.getExecBy().getUserId());

        feedbackDynamoRepository.save(feedback);
    }

    @Override
    public void markAsRead(MarkAsReadCommand markAsReadCommand) {

        log.info(String.format("Entering markAsRead feedback service - Email Id:%s", markAsReadCommand.getEmailId()));

        Feedback feedback = feedbackDynamoRepository.findBy(markAsReadCommand.getEmailId(),markAsReadCommand.getCommentedOn());
        if (feedback != null) {
            feedback.setRead(true);
            feedback.setUpdatedOn(markAsReadCommand.getExecOn());
            feedback.setUpdatedBy(markAsReadCommand.getExecBy().getUserId());
            feedbackDynamoRepository.save(feedback);
        }
    }
    @Override
    public List<Feedback> findAll() {

        log.info("Entering findAll feedback service");
        return feedbackDynamoRepository.findAll();
    }

    @Override
    public int getAllFeedbacksCount() {
        log.info("Entering getall feedbacks count service");
        return feedbackDynamoRepository.getAllFeedbacksCount();
    }




}
