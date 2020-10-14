package com.varabei.ivan.controller.command.impl;

import com.varabei.ivan.Const;
import com.varabei.ivan.controller.command.ActionCommand;
import com.varabei.ivan.model.exception.ServiceException;
import com.varabei.ivan.model.service.AccountService;
import com.varabei.ivan.model.service.CardService;
import com.varabei.ivan.model.service.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class IncludeCards implements ActionCommand {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        CardService cardService = ServiceFactory.getInstance().getCardService();
        try {
            req.setAttribute("cards", cardService.findByAccountId(Long.parseLong(req.getParameter(Const.AccountField.ID))));
            req.getRequestDispatcher("/WEB-INF/pages/includeCards.jsp").include(req, resp);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }
}
