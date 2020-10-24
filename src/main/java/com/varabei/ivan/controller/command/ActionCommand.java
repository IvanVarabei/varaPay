package com.varabei.ivan.controller.command;

import com.varabei.ivan.controller.Router;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface ActionCommand {
    Router execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException;
}
