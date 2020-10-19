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
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class VerifyCreateCardCommand implements ActionCommand {
    private static final Logger log = LogManager.getLogger(VerifyCreateCardCommand.class);
    private static final CardService cardService = ServiceFactory.getInstance().getCardService();
    private static final String REDIRECT_TO_PROFILE = "%s/mainServlet?command=profile_get&cvc=%s";
    private static final String JSP_VERIFY_CREATE_CARD = "/WEB-INF/pages/verifyCreateCard.jsp";

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        HttpSession session = req.getSession();
        String tempCode = req.getParameter(Const.RequestParam.TEMP_CODE);
        Long accountId = Long.parseLong(session.getAttribute(Const.AccountField.ID).toString());
        if (tempCode.equals(session.getAttribute(Const.RequestParam.TEMP_CODE).toString())) {
            try {
                String cvc = cardService.createCardAndReturnCvc(accountId);
                resp.sendRedirect(String.format(REDIRECT_TO_PROFILE, req.getContextPath(), cvc));
            } catch (ServiceException e) {
                log.error(e);
                resp.sendError(Const.ErrorInfo.SERVER_ERROR_CODE);
            }
        } else {
            req.setAttribute(Const.AttributeKey.ERROR, Const.ErrorInfo.WRONG_TEMP_CODE);
            req.getRequestDispatcher(JSP_VERIFY_CREATE_CARD).forward(req, resp);
        }
    }
}
