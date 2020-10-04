package com.varabei.ivan.controller.command.client;

import com.varabei.ivan.controller.command.ActionCommand;
import com.varabei.ivan.controller.command.impl.LoginCommand;
import com.varabei.ivan.controller.command.impl.LogoutCommand;
import com.varabei.ivan.controller.command.impl.RegisterCommand;


public enum CommandType {
    REGISTER(new RegisterCommand()),
    LOGIN(new LoginCommand()),
    LOGOUT(new LogoutCommand());
    private final ActionCommand command;

    CommandType(ActionCommand command) {
        this.command = command;
    }

    public ActionCommand getCurrentCommand() {
        return command;
    }
}
