package com.varabei.ivan.controller.command.impl;

import com.varabei.ivan.controller.command.ActionCommand;
import com.varabei.ivan.model.exception.ServiceException;
import com.varabei.ivan.model.service.ServiceFactory;
import com.varabei.ivan.model.service.BidService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;

public class PlaceTopUpBidCommand implements ActionCommand {
    private static final BidService BID_SERVICE = ServiceFactory.getInstance().getToUpBidService();
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Long accountId = Long.parseLong(req.getParameter("account_id"));
        BigDecimal amount = new BigDecimal(req.getParameter("amount"));
        //String message = req.getParameter("message");
        try {
            BID_SERVICE.placeTopUpBid(accountId , amount, "message");
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        resp.sendRedirect(req.getContextPath() + "/mainServlet?command=profile_get");
    }
}
