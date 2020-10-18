package com.varabei.ivan.controller.command.impl;

import com.varabei.ivan.Const;
import com.varabei.ivan.controller.command.ActionCommand;
import com.varabei.ivan.model.exception.ServiceException;
import com.varabei.ivan.model.service.CardService;
import com.varabei.ivan.model.service.ServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteCardCommand implements ActionCommand {
    private static final Logger log = LogManager.getLogger(DeleteCardCommand.class);
    private static final CardService cardService = ServiceFactory.getInstance().getCardService();
    private static final String REDIRECT_TO_PROFILE = "%s/mainServlet?command=profile_get";

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Long cardId = Long.parseLong(req.getParameter(Const.CardField.ID));
        try {
            cardService.delete(cardId);
            resp.sendRedirect(String.format(REDIRECT_TO_PROFILE, req.getContextPath()));
        } catch (ServiceException e) {
            log.error(e);
            resp.sendError(Const.ErrorInfo.SERVER_ERROR_CODE);
        }
    }
}
