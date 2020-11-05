package com.epam.varapay.controller.command;

import com.epam.varapay.controller.router.Router;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface ActionCommand {
    int RECORDS_PER_PAGE = 2;
    int DEFAULT_PAGE_INDEX = 1;

    Router execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException;
}
