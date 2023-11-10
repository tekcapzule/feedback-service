package com.tekcapzule.feedback.domain.model;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tekcapzule.core.domain.AggregateRoot;
import com.tekcapzule.core.domain.BaseDomainEntity;
import lombok.*;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@EqualsAndHashCode(callSuper = true)
@DynamoDBTable(tableName = "Feedback")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Feedback extends BaseDomainEntity implements AggregateRoot {

    @DynamoDBHashKey(attributeName = "emailId")
    private String emailId;
    @DynamoDBRangeKey(attributeName="commentedOn")
    private String commentedOn;
    @DynamoDBAttribute(attributeName = "firstName")
    private String firstName;
    @DynamoDBAttribute(attributeName = "lastName")
    private String lastName;
    @DynamoDBAttribute(attributeName="subject")
    private String subject;
    @DynamoDBAttribute(attributeName="description")
    private String description;
    @DynamoDBAttribute(attributeName="read")
    private boolean read;

}