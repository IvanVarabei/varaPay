package com.epam.varapay.controller.command.impl;

import com.epam.varapay.controller.CommandPath;
import com.epam.varapay.controller.JspPath;
import com.epam.varapay.controller.RequestParam;
import com.epam.varapay.controller.command.ActionCommand;
import com.epam.varapay.controller.router.Router;
import com.epam.varapay.controller.router.RouterType;
import com.epam.varapay.exception.ServiceException;
import com.epam.varapay.model.service.BidService;
import com.epam.varapay.model.service.ServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ApproveBidCommand implements ActionCommand {
    private static final Logger log = LogManager.getLogger(ApproveBidCommand.class);
    private static BidService bidService = ServiceFactory.getInstance().getBidService();

    @Override
    public Router execute(HttpServletRequest req, HttpServletResponse resp) {
        Router router = new Router(String.format(CommandPath.RUN_BIDS, req.getContextPath(),
                req.getParameter(RequestParam.PAGE)), RouterType.REDIRECT);
        try {
            String adminComment = req.getParameter(RequestParam.ADMIN_COMMENT);
            bidService.approveBid(Long.parseLong(req.getParameter(RequestParam.BID_ID)), adminComment);
        } catch (ServiceException e) {
            log.error(e);
            router.setForward(JspPath.ERROR_500);
        }
        return router;
    }
}
