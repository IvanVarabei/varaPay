package com.epam.varapay.controller.command.impl;

import com.epam.varapay.controller.AttributeKey;
import com.epam.varapay.controller.JspPath;
import com.epam.varapay.controller.RequestParam;
import com.epam.varapay.controller.command.ActionCommand;
import com.epam.varapay.controller.router.Router;
import com.epam.varapay.model.entity.Bid;
import com.epam.varapay.exception.ServiceException;
import com.epam.varapay.model.service.BidService;
import com.epam.varapay.model.service.ServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class RunBidsCommand implements ActionCommand {
    private static final Logger log = LogManager.getLogger(RunBidsCommand.class);
    private static BidService bidService = ServiceFactory.getInstance().getBidService();

    @Override
    public Router execute(HttpServletRequest req, HttpServletResponse resp) {
        Router router = new Router(JspPath.RUN_BIDS);
        try {
            int page = DEFAULT_PAGE_INDEX;
            String pageString = req.getParameter(RequestParam.PAGE);
            if (pageString != null && Integer.parseInt(pageString) != 0) {
                page = Integer.parseInt(req.getParameter(RequestParam.PAGE));
            }
            List<Bid> bids =
                    bidService.findInProgressBids(RECORDS_PER_PAGE, page);
            int amountOfPages = bidService.findAmountOfPages(RECORDS_PER_PAGE);
            req.setAttribute(AttributeKey.AMOUNT_OF_PAGES, amountOfPages);
            req.setAttribute(AttributeKey.CURRENT_PAGE, page);
            req.setAttribute(AttributeKey.BIDS, bids);
        } catch (ServiceException e) {
            log.error(e);
            router.setForward(JspPath.ERROR_500);
        }
        return router;
    }
}
