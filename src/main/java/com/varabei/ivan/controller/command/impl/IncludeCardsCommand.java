package com.varabei.ivan.controller.command.impl;

import com.varabei.ivan.controller.AttributeKey;
import com.varabei.ivan.controller.JspPath;
import com.varabei.ivan.controller.Router;
import com.varabei.ivan.controller.RouterType;
import com.varabei.ivan.controller.command.ActionCommand;
import com.varabei.ivan.model.entity.Card;
import com.varabei.ivan.model.entity.name.AccountField;
import com.varabei.ivan.model.exception.ServiceException;
import com.varabei.ivan.model.service.CardService;
import com.varabei.ivan.model.service.ServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class IncludeCardsCommand implements ActionCommand {
    private static final Logger log = LogManager.getLogger(IncludeCardsCommand.class);

    @Override
    public Router execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Router router = new Router(JspPath.INCLUDE_CARDS, RouterType.INCLUDE);
        CardService cardService = ServiceFactory.getInstance().getCardService();
        try {
            List<Card> cardList = cardService.findByAccountId(Long.parseLong(req.getParameter(AccountField.ID)));
            req.setAttribute(AttributeKey.CARDS, cardList);
        } catch (ServiceException e) {
            log.error(e);
            router.setForward(JspPath.ERROR_500);
        }
        return router;
    }
}
