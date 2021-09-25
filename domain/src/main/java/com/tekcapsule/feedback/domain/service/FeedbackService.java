package com.tekcapsule.feedback.domain.service;

import com.tekcapsule.feedback.domain.command.CreateCommand;
import com.tekcapsule.feedback.domain.command.DisableCommand;
import com.tekcapsule.feedback.domain.model.Feedback;

import java.util.List;


public interface FeedbackService {

    Feedback create(CreateCommand createCommand);

    void markAsRead(DisableCommand disableCommand);

    List<Feedback> findAll();
}
