package com.varabei.ivan.controller.command.impl;

import com.varabei.ivan.Const;
import com.varabei.ivan.controller.command.ActionCommand;
import com.varabei.ivan.model.entity.Card;
import com.varabei.ivan.model.entity.Payment;
import com.varabei.ivan.model.exception.ServiceException;
import com.varabei.ivan.model.service.CardService;
import com.varabei.ivan.model.service.PaymentService;
import com.varabei.ivan.model.service.ServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class CardPageCommand implements ActionCommand {
    private static final Logger log = LogManager.getLogger(CardPageCommand.class);
    private static final String JSP_CARD_PAGE = "/WEB-INF/pages/card.jsp";
    private static CardService cardService = ServiceFactory.getInstance().getCardService();
    private static PaymentService paymentService = ServiceFactory.getInstance().getPaymentService();
    private static final int DEFAULT_PAGE_INDEX = 1;

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Long cardId = Long.parseLong(req.getParameter(Const.CardField.ID));
        try {
            int page = DEFAULT_PAGE_INDEX;
            if (req.getParameter(Const.RequestParam.PAGE) != null) {
                page = Integer.parseInt(req.getParameter(Const.RequestParam.PAGE));
            }
            Card card = cardService.findById(cardId).orElse(null);
            List<Payment> payments =
                    paymentService.findPaymentsByCardId(cardId, Const.WebPageConfig.RECORDS_PER_PAGE, page);
            int amountOfPages = paymentService.findAmountOfPagesByCardId(cardId, Const.WebPageConfig.RECORDS_PER_PAGE);
            req.setAttribute(Const.AttributeKey.CARD, card);
            req.setAttribute(Const.AttributeKey.AMOUNT_OF_PAGES, amountOfPages);
            req.setAttribute(Const.AttributeKey.CURRENT_PAGE, page);
            req.setAttribute(Const.AttributeKey.PAYMENTS, payments);
            req.getRequestDispatcher(JSP_CARD_PAGE).forward(req, resp);
        } catch (ServiceException e) {
            log.error(e);
            resp.sendError(Const.ErrorInfo.SERVER_ERROR_CODE);
        }
    }
}
