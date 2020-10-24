package com.varabei.ivan.controller.command.impl;

import com.varabei.ivan.controller.*;
import com.varabei.ivan.controller.command.ActionCommand;
import com.varabei.ivan.model.entity.Card;
import com.varabei.ivan.model.entity.Payment;
import com.varabei.ivan.model.entity.name.CardField;
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
    private static CardService cardService = ServiceFactory.getInstance().getCardService();
    private static PaymentService paymentService = ServiceFactory.getInstance().getPaymentService();
    private static final int DEFAULT_PAGE_INDEX = 1;

    @Override
    public Router execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Router router = new Router(JspPath.CARD);
        Long cardId = Long.parseLong(req.getParameter(CardField.ID));
        try {
            int page = DEFAULT_PAGE_INDEX;
            if (req.getParameter(RequestParam.PAGE) != null) {
                page = Integer.parseInt(req.getParameter(RequestParam.PAGE));
            }
            Card card = cardService.findById(cardId).orElse(null);
            List<Payment> payments =
                    paymentService.findPaymentsByCardId(cardId, WebPageConfig.RECORDS_PER_PAGE, page);
            int amountOfPages = paymentService.findAmountOfPagesByCardId(cardId, WebPageConfig.RECORDS_PER_PAGE);
            req.setAttribute(AttributeKey.CARD, card);
            req.setAttribute(AttributeKey.AMOUNT_OF_PAGES, amountOfPages);
            req.setAttribute(AttributeKey.CURRENT_PAGE, page);
            req.setAttribute(AttributeKey.PAYMENTS, payments);
        } catch (ServiceException e) {
            log.error(e);
            router.setForward(JspPath.ERROR_500);
        }
        return router;
    }
}
