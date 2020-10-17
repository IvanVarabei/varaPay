package com.varabei.ivan.controller.command.impl;

import com.varabei.ivan.Const;
import com.varabei.ivan.controller.command.ActionCommand;
import com.varabei.ivan.model.exception.ServiceException;
import com.varabei.ivan.model.service.CardService;
import com.varabei.ivan.model.service.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteCardCommand implements ActionCommand {
    private CardService cardService = ServiceFactory.getInstance().getCardService();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Long cardId = Long.parseLong(req.getParameter(Const.CardField.ID));
        try {
            cardService.delete(cardId);
            resp.sendRedirect(req.getContextPath() + "/mainServlet?command=profile_get");
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }
}
