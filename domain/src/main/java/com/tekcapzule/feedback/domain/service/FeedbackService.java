package com.tekcapzule.feedback.domain.service;

import com.tekcapzule.feedback.domain.command.CreateCommand;
import com.tekcapzule.feedback.domain.command.MarkAsReadCommand;
import com.tekcapzule.feedback.domain.model.Feedback;

import java.util.List;


public interface FeedbackService {

    void create(CreateCommand createCommand);

    void markAsRead(MarkAsReadCommand markAsReadCommand);

    List<Feedback> findAll();

    int getAllFeedbacksCount();


}
