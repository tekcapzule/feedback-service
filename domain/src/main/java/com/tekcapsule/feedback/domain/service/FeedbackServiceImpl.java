package com.tekcapsule.feedback.domain.service;

import com.tekcapsule.feedback.domain.command.CreateCommand;
import com.tekcapsule.feedback.domain.command.DisableCommand;
import com.tekcapsule.feedback.domain.model.Feedback;
import com.tekcapsule.feedback.domain.repository.FeedbackDynamoRepository;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
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

        log.info(String.format("Entering create mentor service - Tenant Id:{0}, Name:{1}", createCommand.getTenantId(), createCommand.getName().toString()));

        Name name = createCommand.getName();
        if (name != null) {
            name.setDisplayName(String.format("{0} {1}", name.getFirstName(), name.getLastName()));
        }
        DateOfBirth dateOfBirth = createCommand.getDateOfBirth();
        if (dateOfBirth != null) {
            dateOfBirth.setDateOfBirth(String.format("{0}/{1}/{2}", dateOfBirth.getDay(), dateOfBirth.getMonth(), dateOfBirth.getYear()));
        }
        Feedback feedback = Feedback.builder()
                .active(true)
                .activeSince(DateTime.now(DateTimeZone.UTC).toString())
                .blocked(false)
                .awards(createCommand.getAwards())
                .certifications(createCommand.getCertifications())
                .contact(createCommand.getContact())
                .dateOfBirth(dateOfBirth)
                .educationalQualifications(createCommand.getEducationalQualifications())
                .headLine(createCommand.getHeadLine())
                .name(name)
                .professionalExperiences(createCommand.getProfessionalExperiences())
                .publications(createCommand.getPublications())
                .social(createCommand.getSocial())
                .tags(createCommand.getTags())
                .tenantId(createCommand.getTenantId())
                .userId(createCommand.getContact().getEmailId())
                .build();

        feedback.setAddedOn(createCommand.getExecOn());
        feedback.setUpdatedOn(createCommand.getExecOn());
        feedback.setAddedBy(createCommand.getExecBy().getUserId());

        return mentorRepository.save(feedback);
    }

    @Override
    public void markAsRead(DisableCommand disableCommand) {

        log.info(String.format("Entering disable mentor service - Tenant Id:{0}, User Id:{1}", disableCommand.getTenantId(), disableCommand.getUserId()));

        feedbackDynamoRepository.disableById(disableCommand.getTenantId(), disableCommand.getUserId());
    }

    @Override
    public List<Feedback> findAll() {

        log.info(String.format("Entering search mentor service - Tenant Id:{0}", searchQuery.getTenantId()));

        return feedbackDynamoRepository.findAll();
    }

}
