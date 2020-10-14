package com.varabei.ivan.controller.command.impl;

import com.varabei.ivan.Const;
import com.varabei.ivan.controller.command.ActionCommand;
import com.varabei.ivan.model.exception.ServiceException;
import com.varabei.ivan.model.service.ServiceFactory;
import com.varabei.ivan.model.service.BidService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ApproveTopUpBidCommand implements ActionCommand {
    private static final BidService bidService = ServiceFactory.getInstance().getToUpBidService();
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        try {
            bidService.approveTopUpBid(Long.parseLong(req.getParameter(Const.BidField.ID)));
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }
}
