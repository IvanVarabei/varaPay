package com.epam.varapay.controller.command.impl;

import com.epam.varapay.controller.AttributeKey;
import com.epam.varapay.controller.CommandPath;
import com.epam.varapay.controller.JspPath;
import com.epam.varapay.controller.RequestParam;
import com.epam.varapay.controller.command.ActionCommand;
import com.epam.varapay.controller.router.Router;
import com.epam.varapay.exception.ServiceException;
import com.epam.varapay.model.service.CardService;
import com.epam.varapay.model.service.ServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class VerifyCreateCardCommand implements ActionCommand {
    private static final Logger log = LogManager.getLogger(VerifyCreateCardCommand.class);
    private static CardService cardService = ServiceFactory.getInstance().getCardService();

    @Override
    public Router execute(HttpServletRequest req, HttpServletResponse resp) {
        Router router = new Router();
        HttpSession session = req.getSession();
        String tempCode = req.getParameter(RequestParam.TEMP_CODE);
        Long accountId = Long.parseLong(session.getAttribute(RequestParam.ACCOUNT_ID).toString());
        if (tempCode.equals(session.getAttribute(RequestParam.TEMP_CODE).toString())) {
            try {
                String cvc = cardService.createCardAndReturnCvc(accountId);
                router.setRedirect(String.format(CommandPath.PROFILE_WITH_CVC, req.getContextPath(), cvc));
            } catch (ServiceException e) {
                log.error(e);
                router.setForward(JspPath.ERROR_500);
            }
        } else {
            req.setAttribute(AttributeKey.ERROR, true);
            router.setForward(JspPath.VERIFY_CREATE_CARD);
        }
        return router;
    }
}
