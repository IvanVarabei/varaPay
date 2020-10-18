package com.varabei.ivan.controller.command.impl;

import com.varabei.ivan.Const;
import com.varabei.ivan.controller.command.ActionCommand;
import com.varabei.ivan.model.entity.Account;
import com.varabei.ivan.model.entity.Bid;
import com.varabei.ivan.model.exception.ServiceException;
import com.varabei.ivan.model.service.ServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class AccountPageCommand implements ActionCommand {
    private static final Logger log = LogManager.getLogger(AccountPageCommand.class);
    private static final String JSP_ACCOUNT = "/WEB-INF/pages/account.jsp";

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Long accountId = Long.parseLong(req.getParameter(Const.AccountField.ID));
        try {
            Account account = ServiceFactory.getInstance().getAccountService().findById(accountId).orElse(null);
            req.setAttribute(Const.AttributeKey.ACCOUNT, account);
            List<Bid> bids = ServiceFactory.getInstance().getToUpBidService().findByAccountId(accountId);
            req.setAttribute(Const.AttributeKey.BIDS, bids);
            req.getRequestDispatcher(JSP_ACCOUNT).forward(req, resp);
        } catch (ServiceException e) {
            log.error(e);
            resp.sendError(Const.ErrorInfo.SERVER_ERROR_CODE);
        }
    }
}
