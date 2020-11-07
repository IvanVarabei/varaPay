package com.epam.varapay.controller.command.impl;

import com.epam.varapay.controller.AttributeKey;
import com.epam.varapay.controller.JspPath;
import com.epam.varapay.controller.RequestParam;
import com.epam.varapay.controller.command.ActionCommand;
import com.epam.varapay.controller.router.Router;
import com.epam.varapay.controller.router.RouterType;
import com.epam.varapay.model.entity.Card;
import com.epam.varapay.model.exception.ServiceException;
import com.epam.varapay.model.service.CardService;
import com.epam.varapay.model.service.ServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class IncludeCardsCommand implements ActionCommand {
    private static final Logger log = LogManager.getLogger(IncludeCardsCommand.class);
    private static CardService cardService = ServiceFactory.getInstance().getCardService();

    @Override
    public Router execute(HttpServletRequest req, HttpServletResponse resp) {
        Router router = new Router(JspPath.INCLUDE_CARDS, RouterType.INCLUDE);
        try {
            Long accountId = Long.parseLong(req.getParameter(RequestParam.ACCOUNT_ID));
            List<Card> cardList = cardService.findByAccountId(accountId);
            req.setAttribute(AttributeKey.CARDS, cardList);
        } catch (ServiceException e) {
            log.error(e);
            router.setForward(JspPath.ERROR_500);
        }
        return router;
    }
}
