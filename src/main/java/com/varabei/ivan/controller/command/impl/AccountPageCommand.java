package com.varabei.ivan.controller.command.impl;

import com.varabei.ivan.controller.AttributeKey;
import com.varabei.ivan.controller.JspPath;
import com.varabei.ivan.controller.RequestParam;
import com.varabei.ivan.controller.WebPageConfig;
import com.varabei.ivan.controller.command.ActionCommand;
import com.varabei.ivan.controller.router.Router;
import com.varabei.ivan.model.entity.Account;
import com.varabei.ivan.model.entity.Bid;
import com.varabei.ivan.model.entity.name.AccountField;
import com.varabei.ivan.model.exception.ServiceException;
import com.varabei.ivan.model.service.AccountService;
import com.varabei.ivan.model.service.BidService;
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
    private static AccountService accountService = ServiceFactory.getInstance().getAccountService();
    private static BidService bidService = ServiceFactory.getInstance().getToUpBidService();

    @Override
    public Router execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Router router = new Router(JspPath.ACCOUNT);
        Long accountId = Long.parseLong(req.getParameter(AccountField.ID));
        try {
            int page = 1;
            if (req.getParameter(RequestParam.PAGE) != null) {
                page = Integer.parseInt(req.getParameter(RequestParam.PAGE));
            }
            List<Bid> bids =
                    bidService.findByAccountId(accountId, WebPageConfig.RECORDS_PER_PAGE, page);
            int amountOfPages = bidService.findAmountOfPagesByAccountId(accountId, WebPageConfig.RECORDS_PER_PAGE);
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
