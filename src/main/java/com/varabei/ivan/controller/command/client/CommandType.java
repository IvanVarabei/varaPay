package com.varabei.ivan.controller.command.client;

import com.varabei.ivan.controller.command.ActionCommand;
import com.varabei.ivan.controller.command.impl.AddBookCommand;

public enum CommandType {
    ADD_BOOK(new AddBookCommand());
    private final ActionCommand command;

    CommandType(ActionCommand command) {
        this.command = command;
    }

    public ActionCommand getCurrentCommand() {
        return command;
    }
}
