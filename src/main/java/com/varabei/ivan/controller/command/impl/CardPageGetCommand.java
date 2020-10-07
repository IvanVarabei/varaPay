package com.varabei.ivan.controller.command.impl;

import com.varabei.ivan.controller.command.ActionCommand;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CardPageGetCommand implements ActionCommand {
    private static final String JSP_CARD_PAGE = "/WEB-INF/pages/card.jsp";
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        req.getRequestDispatcher(JSP_CARD_PAGE).forward(req, resp);
    }
}
