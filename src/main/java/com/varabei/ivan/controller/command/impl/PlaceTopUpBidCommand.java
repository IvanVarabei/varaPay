package com.varabei.ivan.controller.command.impl;

import com.varabei.ivan.common.ErrorInfo;
import com.varabei.ivan.controller.command.ActionCommand;
import com.varabei.ivan.model.entity.name.AccountField;
import com.varabei.ivan.model.entity.name.BidField;
import com.varabei.ivan.model.exception.ServiceException;
import com.varabei.ivan.model.service.BidService;
import com.varabei.ivan.model.service.ServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;

public class PlaceTopUpBidCommand implements ActionCommand {
    private static final Logger log = LogManager.getLogger(PlaceTopUpBidCommand.class);
    private static final BidService bidService = ServiceFactory.getInstance().getToUpBidService();
    private static final String REDIRECT_SUCCESS_PAGE = "%s/mainServlet?command=success_get";

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Long accountId = Long.parseLong(req.getParameter(AccountField.ID));
        BigDecimal amount = new BigDecimal(req.getParameter(BidField.AMOUNT));
        String message = req.getParameter(BidField.CLIENT_MESSAGE);
        try {
            bidService.placeTopUpBid(accountId, amount, message);
            resp.sendRedirect(String.format(REDIRECT_SUCCESS_PAGE, req.getContextPath()));
        } catch (ServiceException e) {
            log.error(e);
            resp.sendError(ErrorInfo.SERVER_ERROR_CODE);
        }
    }
}
