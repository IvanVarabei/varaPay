package com.epam.varapay.controller.command.impl;

import com.epam.varapay.controller.AttributeKey;
import com.epam.varapay.controller.JspPath;
import com.epam.varapay.controller.RequestParam;
import com.epam.varapay.controller.command.ActionCommand;
import com.epam.varapay.controller.router.Router;
import com.epam.varapay.model.entity.Card;
import com.epam.varapay.model.entity.Payment;
import com.epam.varapay.exception.ServiceException;
import com.epam.varapay.model.service.CardService;
import com.epam.varapay.model.service.PaymentService;
import com.epam.varapay.model.service.ServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class CardPageCommand implements ActionCommand {
    private static final Logger log = LogManager.getLogger(CardPageCommand.class);
    private static CardService cardService = ServiceFactory.getInstance().getCardService();
    private static PaymentService paymentService = ServiceFactory.getInstance().getPaymentService();

    @Override
    public Router execute(HttpServletRequest req, HttpServletResponse resp) {
        Router router = new Router(JspPath.CARD);
        Long cardId = Long.parseLong(req.getParameter(RequestParam.CARD_ID));
        try {
            int page = DEFAULT_PAGE_INDEX;
            if (req.getParameter(RequestParam.PAGE) != null) {
                page = Integer.parseInt(req.getParameter(RequestParam.PAGE));
            }
            Card card = cardService.findById(cardId).get();
            List<Payment> payments =
                    paymentService.findPaymentsByCardId(cardId, RECORDS_PER_PAGE, page);
            int amountOfPages = paymentService.findAmountOfPagesByCardId(cardId, RECORDS_PER_PAGE);
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
