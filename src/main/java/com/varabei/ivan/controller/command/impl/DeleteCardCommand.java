package com.varabei.ivan.controller.command.impl;

import com.varabei.ivan.controller.JspPath;
import com.varabei.ivan.controller.command.ActionCommand;
import com.varabei.ivan.controller.RedirectPath;
import com.varabei.ivan.controller.router.Router;
import com.varabei.ivan.controller.router.RouterType;
import com.varabei.ivan.model.entity.name.CardField;
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
    private static CardService cardService = ServiceFactory.getInstance().getCardService();

    @Override
    public Router execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Router router = new Router(String.format(RedirectPath.PROFILE, req.getContextPath()), RouterType.REDIRECT);
        Long cardId = Long.parseLong(req.getParameter(CardField.ID));
        try {
            cardService.delete(cardId);
        } catch (ServiceException e) {
            log.error(e);
            router.setForward(JspPath.ERROR_500);
        }
        return router;
    }
}
