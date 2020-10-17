package com.varabei.ivan.controller.command.impl;

import com.varabei.ivan.Const;
import com.varabei.ivan.controller.command.ActionCommand;
import com.varabei.ivan.model.entity.Card;
import com.varabei.ivan.model.entity.Payment;
import com.varabei.ivan.model.service.CardService;
import com.varabei.ivan.model.exception.ServiceException;
import com.varabei.ivan.model.service.PaymentService;
import com.varabei.ivan.model.service.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class CardPageCommand implements ActionCommand {
    private static final String JSP_CARD_PAGE = "/WEB-INF/pages/card.jsp";
    private static CardService cardService = ServiceFactory.getInstance().getCardService();
    private static PaymentService paymentService = ServiceFactory.getInstance().getPaymentService();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Long cardId = Long.parseLong(req.getParameter(Const.CardField.ID));
        try {
            int page = 1;
            int recordsPerPage = 1;
            if(req.getParameter("page") != null) {
                page = Integer.parseInt(req.getParameter("page"));
            }
            List<Payment> list = paymentService.findPaymentsByCardId(cardId, recordsPerPage,(page-1)*recordsPerPage);
            Long noOfRecords = paymentService.findNumberOfRecordsByCardId(cardId);
            int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
            req.setAttribute("noOfPages", noOfPages);
            req.setAttribute("currentPage", page);

            Card card = cardService.findById(cardId).get();
            req.setAttribute("card", card);
            req.setAttribute("payments", list);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        req.getRequestDispatcher(JSP_CARD_PAGE).forward(req, resp);
    }
}
