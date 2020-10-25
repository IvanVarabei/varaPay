package com.varabei.ivan.controller.command.impl;

import com.varabei.ivan.common.ErrorInfo;
import com.varabei.ivan.controller.AttributeKey;
import com.varabei.ivan.controller.JspPath;
import com.varabei.ivan.controller.RequestParam;
import com.varabei.ivan.controller.command.ActionCommand;
import com.varabei.ivan.controller.RedirectPath;
import com.varabei.ivan.controller.router.Router;
import com.varabei.ivan.controller.router.RouterType;
import com.varabei.ivan.model.entity.Card;
import com.varabei.ivan.model.entity.name.CardField;
import com.varabei.ivan.model.entity.name.PaymentField;
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
import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class MakePaymentCommand implements ActionCommand {
    private static final Logger log = LogManager.getLogger(MakePaymentCommand.class);
    private static PaymentService paymentService = ServiceFactory.getInstance().getPaymentService();
    private static CardService cardService = ServiceFactory.getInstance().getCardService();

    @Override
    public Router execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Router router = new Router(String.format(RedirectPath.SUCCESS_PAGE, req.getContextPath()), RouterType.REDIRECT);
        Long sourceCardId = Long.parseLong(req.getParameter(CardField.ID));
        String sourceCardCvc = req.getParameter(CardField.CVC);
        String destinationCardNumber = req.getParameter(RequestParam.DESTINATION_CARD_NUMBER);
        BigDecimal amount = BigDecimal.valueOf(Double.parseDouble(req.getParameter(PaymentField.AMOUNT)));
        YearMonth destinationCardValidThru = YearMonth.parse(req.getParameter(RequestParam.DESTINATION_CARD_VALID_THRU));
        Map<String, String> errors = new HashMap<>();
        try {
            Card sourceCard = cardService.findById(sourceCardId).get();
            Optional<Card> destinationCard = cardService.findByCardNumber(destinationCardNumber);
            if (destinationCard.isPresent()) {
                if (!destinationCard.get().getAccount().isActive()) {
                    errors.put("destinationIsActive", ErrorInfo.DESTINATION_ACCOUNT_BLOCKED);
                }
            } else {
                errors.put(CardField.NUMBER, ErrorInfo.WRONG_CARD_NUMBER);
            }
            if (!sourceCard.getCvc().equals(sourceCardCvc)) {
                errors.put(CardField.CVC, ErrorInfo.WRONG_CVC);
            }
            if (sourceCard.getAccount().getBalance().compareTo(amount) < 0) {
                errors.put(PaymentField.AMOUNT, ErrorInfo.NOT_ENOUGH_BALANCE);
            }
            if (!sourceCard.getAccount().isActive()) {
                errors.put("sourceIsActive", ErrorInfo.SOURCE_ACCOUNT_BLOCKED);
            }
            if (errors.isEmpty()) {
                paymentService.makePayment(sourceCardId, sourceCardCvc,
                        destinationCardNumber, destinationCardValidThru, amount);
            } else {
                req.setAttribute(AttributeKey.ERRORS, errors);
                router.setForward("");
            }
        } catch (ServiceException e) {
            log.error(e);
            router.setForward(JspPath.ERROR_500);
        }
        return router;
    }
}
