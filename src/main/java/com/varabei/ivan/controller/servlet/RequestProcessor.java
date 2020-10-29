package com.varabei.ivan.controller.servlet;

import com.varabei.ivan.controller.RequestParam;
import com.varabei.ivan.controller.command.ActionCommand;
import com.varabei.ivan.controller.command.provider.ActionProvider;
import com.varabei.ivan.controller.router.Router;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RequestProcessor extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ActionCommand actionCommand = ActionProvider.defineAction(req.getParameter(RequestParam.COMMAND));
        Router router = actionCommand.execute(req, resp);
        switch (router.getType()) {
            case FORWARD -> req.getRequestDispatcher(router.getPage()).forward(req, resp);
            case REDIRECT -> resp.sendRedirect(router.getPage());
            case INCLUDE -> req.getRequestDispatcher(router.getPage()).include(req, resp);
        }
    }
}
