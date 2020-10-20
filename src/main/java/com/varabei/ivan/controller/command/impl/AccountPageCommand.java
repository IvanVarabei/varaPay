package com.varabei.ivan.controller.command.impl;

import com.varabei.ivan.common.ErrorInfo;
import com.varabei.ivan.controller.AttributeKey;
import com.varabei.ivan.controller.command.ActionCommand;
import com.varabei.ivan.model.entity.Account;
import com.varabei.ivan.model.entity.Bid;
import com.varabei.ivan.model.entity.name.AccountField;
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
        Long accountId = Long.parseLong(req.getParameter(AccountField.ID));
        try {
            Account account = ServiceFactory.getInstance().getAccountService().findById(accountId).orElse(null);
            req.setAttribute(AttributeKey.ACCOUNT, account);
            List<Bid> bids = ServiceFactory.getInstance().getToUpBidService().findByAccountId(accountId);
            req.setAttribute(AttributeKey.BIDS, bids);
            req.getRequestDispatcher(JSP_ACCOUNT).forward(req, resp);
        } catch (ServiceException e) {
            log.error(e);
            resp.sendError(ErrorInfo.SERVER_ERROR_CODE);
        }
    }
}
