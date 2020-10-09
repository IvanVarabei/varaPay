package com.varabei.ivan.controller.command.impl;

import com.varabei.ivan.Const;
import com.varabei.ivan.controller.command.ActionCommand;
import com.varabei.ivan.model.service.CardService;
import com.varabei.ivan.model.service.ServiceException;
import com.varabei.ivan.model.service.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CardPageGetCommand implements ActionCommand {
    private static final String JSP_CARD_PAGE = "/WEB-INF/pages/card.jsp";
    private static final CardService cardService = ServiceFactory.getInstance().getCardService();
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Long cardId = Long.parseLong(req.getParameter(Const.CardField.ID));
        try {
            req.setAttribute("card", cardService.findById(cardId).get());
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        req.getRequestDispatcher(JSP_CARD_PAGE).forward(req, resp);
    }
}
