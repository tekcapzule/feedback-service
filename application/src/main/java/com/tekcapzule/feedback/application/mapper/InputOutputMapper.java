package com.tekcapzule.feedback.application.mapper;

import com.tekcapzule.core.domain.Command;
import com.tekcapzule.core.domain.ExecBy;
import com.tekcapzule.core.domain.Origin;
import com.tekcapzule.feedback.application.function.input.CreateInput;
import com.tekcapzule.feedback.application.function.input.MarkAsReadInput;
import com.tekcapzule.feedback.domain.command.CreateCommand;
import com.tekcapzule.feedback.domain.command.MarkAsReadCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.function.BiFunction;

@Slf4j
public final class InputOutputMapper {
    private InputOutputMapper() {

    }

    public static final BiFunction<Command, Origin, Command> addOrigin = (command, origin) -> {
        OffsetDateTime utc = OffsetDateTime.now(ZoneOffset.UTC);
        command.setChannel(origin.getChannel());
        command.setExecBy(ExecBy.builder().tenantId(origin.getTenantId()).userId(origin.getUserId()).build());
        command.setExecOn(utc.toString());
        return command;
    };

    public static final BiFunction<CreateInput, Origin, CreateCommand> buildCreateCommandFromCreateInput = (createInput, origin) -> {
        CreateCommand createCommand =  CreateCommand.builder().build();
        BeanUtils.copyProperties(createInput, createCommand);
        addOrigin.apply(createCommand, origin);
        return createCommand;
    };

    public static final BiFunction<MarkAsReadInput, Origin, MarkAsReadCommand> buildMarkAsReadCommandFromMarkAsReadInput = (markAsReadInput, origin) -> {
        MarkAsReadCommand markAsReadCommand =  MarkAsReadCommand.builder().build();
        BeanUtils.copyProperties(markAsReadInput, markAsReadCommand);
        addOrigin.apply(markAsReadCommand, origin);
        return markAsReadCommand;
    };

}
