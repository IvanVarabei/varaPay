package com.varabei.ivan.controller.command.impl;

import com.varabei.ivan.common.ErrorInfo;
import com.varabei.ivan.controller.AttributeKey;
import com.varabei.ivan.controller.command.ActionCommand;
import com.varabei.ivan.model.exception.ServiceException;
import com.varabei.ivan.model.service.BidService;
import com.varabei.ivan.model.service.ServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RunBidsCommand implements ActionCommand {
    private static final Logger log = LogManager.getLogger(RunBidsCommand.class);
    private static final BidService bidService = ServiceFactory.getInstance().getToUpBidService();
    private static final String JSP_RUN_BIDS = "/WEB-INF/pages/runBids.jsp";

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        try {
            req.setAttribute(AttributeKey.BIDS, bidService.findInProgressBids());
            req.getRequestDispatcher(JSP_RUN_BIDS).forward(req, resp);
        } catch (ServiceException e) {
            log.error(e);
            resp.sendError(ErrorInfo.SERVER_ERROR_CODE);
        }
    }
}
