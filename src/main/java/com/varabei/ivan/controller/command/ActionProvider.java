package com.varabei.ivan.controller.command;

import com.varabei.ivan.controller.command.impl.WelcomeCommand;

public class ActionProvider {
    private ActionProvider() {
    }

    public static ActionCommand defineAction(String action) {
        ActionCommand command;
        try {
            if (action != null) {
                command = CommandType.valueOf(action.toUpperCase()).getCurrentCommand();
            } else {
                command = new WelcomeCommand();
            }
        } catch (IllegalArgumentException e) {
            command = new WelcomeCommand();
        }
        return command;
    }
}
