package com.varabei.ivan.controller.command.impl;

import com.varabei.ivan.controller.AttributeKey;
import com.varabei.ivan.controller.JspPath;
import com.varabei.ivan.controller.RedirectPath;
import com.varabei.ivan.controller.command.ActionCommand;
import com.varabei.ivan.controller.router.Router;
import com.varabei.ivan.controller.router.RouterType;
import com.varabei.ivan.model.entity.name.CardField;
import com.varabei.ivan.model.entity.name.PaymentField;
import com.varabei.ivan.model.exception.ServiceException;
import com.varabei.ivan.model.service.PaymentService;
import com.varabei.ivan.model.service.ServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MakePaymentCommand implements ActionCommand {
    private static final Logger log = LogManager.getLogger(MakePaymentCommand.class);
    private static PaymentService paymentService = ServiceFactory.getInstance().getPaymentService();

    @Override
    public Router execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Router router = new Router(String.format(RedirectPath.SUCCESS_PAGE, req.getContextPath()), RouterType.REDIRECT);
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put(CardField.ID, req.getParameter(CardField.ID));
        paymentData.put(CardField.CVC, req.getParameter(CardField.CVC));
        paymentData.put(CardField.NUMBER, req.getParameter(PaymentField.DESTINATION_CARD_NUMBER));
        paymentData.put(PaymentField.AMOUNT, req.getParameter(PaymentField.AMOUNT));
        paymentData.put(CardField.VALID_THRU, req.getParameter(CardField.VALID_THRU));
        try {
            if(!paymentService.makePayment(paymentData)){
                req.setAttribute(AttributeKey.ERRORS, paymentData);
                router.setForward("/mainServlet?command=card_page_get");
            }
        } catch (ServiceException e) {
            log.error(e);
            router.setForward(JspPath.ERROR_500);
        }
        return router;
    }
}
