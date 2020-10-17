package com.varabei.ivan.controller.command.impl;

import com.varabei.ivan.Const;
import com.varabei.ivan.controller.command.ActionCommand;
import com.varabei.ivan.model.service.PaymentService;
import com.varabei.ivan.model.exception.ServiceException;
import com.varabei.ivan.model.service.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;

public class MakePaymentCommand implements ActionCommand {
    private static final String JSP_CARD_PAGE = "/mainServlet?command=payment_overview_get";
    private static final PaymentService paymentService = ServiceFactory.getInstance().getPaymentService();
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String s = req.getParameter(Const.CardField.ID);
        Long sourceCardId = Long.parseLong(s);
        String destinationCardNumber= req.getParameter("destinationCardNumber");
        String sourceCardCvc= req.getParameter(Const.CardField.CVC);
        BigDecimal amount = BigDecimal.valueOf(Double.parseDouble(req.getParameter("amount")));
        //LocalDate destinationCardDate= req.getParameter("destinationCardDate");
        try {
            paymentService.makePayment(sourceCardId, destinationCardNumber, amount);
            resp.sendRedirect(req.getContextPath()+  JSP_CARD_PAGE);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }
}
