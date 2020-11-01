package com.varabei.ivan.controller.command.impl;

import com.varabei.ivan.controller.CommandPath;
import com.varabei.ivan.controller.JspPath;
import com.varabei.ivan.controller.RequestParam;
import com.varabei.ivan.controller.command.ActionCommand;
import com.varabei.ivan.controller.router.Router;
import com.varabei.ivan.controller.router.RouterType;
import com.varabei.ivan.model.exception.ServiceException;
import com.varabei.ivan.model.service.BidService;
import com.varabei.ivan.model.service.ServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ApproveBidCommand implements ActionCommand {
    private static final Logger log = LogManager.getLogger(ApproveBidCommand.class);
    private static BidService bidService = ServiceFactory.getInstance().getBidService();

    @Override
    public Router execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Router router = new Router(String.format(CommandPath.RUN_BIDS, req.getContextPath(),
                req.getParameter(RequestParam.PAGE)), RouterType.REDIRECT);
        try {
            bidService.approveBid(Long.parseLong(req.getParameter(RequestParam.BID_ID)));
        } catch (ServiceException e) {
            log.error(e);
            router.setForward(JspPath.ERROR_500);
        }
        return router;
    }
}