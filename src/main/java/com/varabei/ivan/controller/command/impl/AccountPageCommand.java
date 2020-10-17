package com.varabei.ivan.controller.command.impl;

import com.varabei.ivan.Const;
import com.varabei.ivan.controller.command.ActionCommand;
import com.varabei.ivan.model.entity.Bid;
import com.varabei.ivan.model.exception.ServiceException;
import com.varabei.ivan.model.service.AccountService;
import com.varabei.ivan.model.service.BidService;
import com.varabei.ivan.model.service.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class AccountPageCommand implements ActionCommand {
    String JSP_ACCOUNT = "/WEB-INF/pages/account.jsp";
    AccountService accountService = ServiceFactory.getInstance().getAccountService();
    BidService bidService = ServiceFactory.getInstance().getToUpBidService();
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Long accountId = Long.parseLong(req.getParameter(Const.AccountField.ID));
        try {
            List<Bid> b =bidService.findByAccountId(accountId);
            req.setAttribute("account", accountService.findById(accountId).get());
            req.setAttribute("bids", bidService.findByAccountId(accountId));
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        req.getRequestDispatcher(JSP_ACCOUNT).forward(req, resp);
    }
}
