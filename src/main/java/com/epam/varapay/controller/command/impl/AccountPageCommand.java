package com.epam.varapay.controller.command.impl;

import com.epam.varapay.controller.RequestParam;
import com.epam.varapay.controller.AttributeKey;
import com.epam.varapay.controller.JspPath;
import com.epam.varapay.controller.command.ActionCommand;
import com.epam.varapay.controller.router.Router;
import com.epam.varapay.model.entity.Account;
import com.epam.varapay.model.entity.Bid;
import com.epam.varapay.model.exception.ServiceException;
import com.epam.varapay.model.service.AccountService;
import com.epam.varapay.model.service.BidService;
import com.epam.varapay.model.service.ServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class AccountPageCommand implements ActionCommand {
    private static final Logger log = LogManager.getLogger(AccountPageCommand.class);
    private static AccountService accountService = ServiceFactory.getInstance().getAccountService();
    private static BidService bidService = ServiceFactory.getInstance().getBidService();

    @Override
    public Router execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Router router = new Router(JspPath.ACCOUNT);
        Long accountId = Long.parseLong(req.getParameter(RequestParam.ACCOUNT_ID));
        try {
            int page = DEFAULT_PAGE_INDEX;
            if (req.getParameter(RequestParam.PAGE) != null) {
                page = Integer.parseInt(req.getParameter(RequestParam.PAGE));
            }
            List<Bid> bids = bidService.findByAccountId(accountId, RECORDS_PER_PAGE, page);
            int amountOfPages = bidService.findAmountOfPagesByAccountId(accountId, RECORDS_PER_PAGE);
            req.setAttribute(AttributeKey.AMOUNT_OF_PAGES, amountOfPages);
            req.setAttribute(AttributeKey.CURRENT_PAGE, page);
            Account account = accountService.findById(accountId).orElse(null);
            req.setAttribute(AttributeKey.ACCOUNT, account);
            req.setAttribute(AttributeKey.BIDS, bids);
        } catch (ServiceException e) {
            log.error(e);
            router.setForward(JspPath.ERROR_500);
        }
        return router;
    }
}
