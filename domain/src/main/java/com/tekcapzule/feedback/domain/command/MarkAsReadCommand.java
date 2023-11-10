package com.tekcapzule.feedback.domain.command;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tekcapzule.core.domain.Command;
import lombok.Builder;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public class MarkAsReadCommand extends Command {
    private String emailId;
    private String commentedOn;
}
