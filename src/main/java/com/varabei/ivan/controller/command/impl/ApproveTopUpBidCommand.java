package com.varabei.ivan.controller.command.impl;

import com.varabei.ivan.Const;
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

public class ApproveTopUpBidCommand implements ActionCommand {
    private static final Logger log = LogManager.getLogger(ApproveTopUpBidCommand.class);
    private static final BidService bidService = ServiceFactory.getInstance().getToUpBidService();
    private static final String REDIRECT_AFTER_APPROVING = "%s/mainServlet?command=RUN_BIDS_GET";

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        try {
            bidService.approveTopUpBid(Long.parseLong(req.getParameter(Const.BidField.ID)));
            resp.sendRedirect(String.format(REDIRECT_AFTER_APPROVING, req.getContextPath()));
        } catch (ServiceException e) {
            log.error(e);
            resp.sendError(Const.ErrorInfo.SERVER_ERROR_CODE);
        }
    }
}
