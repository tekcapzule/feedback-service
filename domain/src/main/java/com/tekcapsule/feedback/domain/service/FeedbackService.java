package com.tekcapsule.feedback.domain.service;

import com.tekcapsule.feedback.domain.command.CreateCommand;
import com.tekcapsule.feedback.domain.command.MarkAsReadCommand;
import com.tekcapsule.feedback.domain.model.Feedback;

import java.util.List;


public interface FeedbackService {

    void create(CreateCommand createCommand);

    void markAsRead(MarkAsReadCommand markAsReadCommand);

    List<Feedback> findAll();
}
