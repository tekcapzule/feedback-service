package com.tekcapsule.feedback.domain.command;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tekcapsule.core.domain.Command;
import lombok.Builder;
import lombok.Data;


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public class CreateCommand extends Command {
    private String emailId;
    private String firstName;
    private String lastName;
    private String subject;
    private String description;
}
