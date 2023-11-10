package com.tekcapzule.feedback.application.function.input;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
public class CreateInput {
    private String emailId;
    private String firstName;
    private String lastName;
    private String subject;
    private String description;
}
