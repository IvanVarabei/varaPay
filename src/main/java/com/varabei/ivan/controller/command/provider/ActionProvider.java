package com.varabei.ivan.controller.command.provider;

import com.varabei.ivan.controller.command.ActionCommand;
import com.varabei.ivan.controller.command.client.CommandType;
import com.varabei.ivan.controller.command.impl.EmptyCommand;

public class ActionProvider {
    private ActionProvider() {
    }

    public static ActionCommand defineAction(String action) {
        ActionCommand command;
        try {
            command = CommandType.valueOf(action.toUpperCase()).getCurrentCommand();
        } catch (IllegalArgumentException e) {
            command = new EmptyCommand();
        }
        return command;
    }
}
