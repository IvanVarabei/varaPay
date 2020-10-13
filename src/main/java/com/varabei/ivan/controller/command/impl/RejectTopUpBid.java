package com.varabei.ivan.controller.command.impl;

import com.varabei.ivan.controller.command.ActionCommand;
import com.varabei.ivan.model.exception.ServiceException;
import com.varabei.ivan.model.service.ServiceFactory;
import com.varabei.ivan.model.service.BidService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RejectTopUpBid implements ActionCommand {
    private static final BidService BIDSERVICE = ServiceFactory.getInstance().getToUpBidService();
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        try {
            BIDSERVICE.rejectTopUpBid(Long.parseLong(req.getParameter("topUpBidId")));
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }
}
