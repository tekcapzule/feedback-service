package com.tekcapsule.feedback.domain.service;

import com.tekcapsule.feedback.domain.command.CreateCommand;
import com.tekcapsule.feedback.domain.command.MarkAsReadCommand;
import com.tekcapsule.feedback.domain.model.Feedback;
import com.tekcapsule.feedback.domain.repository.FeedbackDynamoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public Feedback create(CreateCommand createCommand) {

        log.info(String.format("Entering create feedback service - Email Id:{0}", createCommand.getEmailId()));

        Feedback feedback = Feedback.builder()
                .description(createCommand.getDescription())
                .emailId(createCommand.getEmailId())
                .firstName(createCommand.getFirstName())
                .lastName(createCommand.getLastName())
                .subject(createCommand.getSubject())
                .read(false)
                .build();

        feedback.setAddedOn(createCommand.getExecOn());

        return feedbackDynamoRepository.save(feedback);
    }

    @Override
    public void markAsRead(MarkAsReadCommand markAsReadCommand) {

        log.info(String.format("Entering markAsRead feedback service - Tenant Id:{0}, User Id:{1}", markAsReadCommand.getTenantId(), markAsReadCommand.getUserId()));

        log.info(String.format("Entering update mentor service - Tenant Id:{0}, User Id:{1}", updateCommand.getTenantId(), updateCommand.getUserId()));

        Feedback feedback = feedbackDynamoRepository.findBy(markAsReadCommand.getEmailId());
        if (feedback != null) {
            mentor.setAwards(updateCommand.getAwards());
            mentor.setHeadLine(updateCommand.getHeadLine());
            mentor.setContact(updateCommand.getContact());
            mentor.setCertifications(updateCommand.getCertifications());
            mentor.setPhotoUrl(updateCommand.getPhotoUrl());
            mentor.setTags(updateCommand.getTags());
            mentor.setSocial(updateCommand.getSocial());
            mentor.setEducationalQualifications(updateCommand.getEducationalQualifications());
            mentor.setProfessionalExperiences(updateCommand.getProfessionalExperiences());
            mentor.setPublications(updateCommand.getPublications());

            mentor.setUpdatedOn(updateCommand.getExecOn());
            mentor.setUpdatedBy(updateCommand.getExecBy().getUserId());

            mentorRepository.save(mentor);
        }
        return mentor;
        feedbackDynamoRepository.(markAsReadCommand.getTenantId(), markAsReadCommand.getUserId());
    }
    @Override
    public List<Feedback> findAll() {

        log.info(String.format("Entering findAll feedback service");

        return feedbackDynamoRepository.findAll();
    }

}
