package com.varabei.ivan.controller.command.impl;

import com.varabei.ivan.common.ErrorInfo;
import com.varabei.ivan.controller.AttributeKey;
import com.varabei.ivan.controller.command.ActionCommand;
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

public class IncludeCardsCommand implements ActionCommand {
    private static final Logger log = LogManager.getLogger(IncludeAccountsCommand.class);
    private static final String JSP_INCLUDE_CARDS = "/WEB-INF/pages/includeCards.jsp";

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        CardService cardService = ServiceFactory.getInstance().getCardService();
        try {
            req.setAttribute(AttributeKey.CARDS, cardService.findByAccountId(Long.parseLong(req.getParameter(AccountField.ID))));
            req.getRequestDispatcher(JSP_INCLUDE_CARDS).include(req, resp);
        } catch (ServiceException e) {
            log.error(e);
            resp.sendError(ErrorInfo.SERVER_ERROR_CODE);
        }
    }
}
