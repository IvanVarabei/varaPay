package com.epam.varapay.controller.command.impl;

import com.epam.varapay.controller.AttributeKey;
import com.epam.varapay.controller.CommandPath;
import com.epam.varapay.controller.JspPath;
import com.epam.varapay.controller.RequestParam;
import com.epam.varapay.controller.command.ActionCommand;
import com.epam.varapay.controller.router.Router;
import com.epam.varapay.controller.router.RouterType;
import com.epam.varapay.model.exception.ServiceException;
import com.epam.varapay.model.service.PaymentService;
import com.epam.varapay.model.service.ServiceFactory;
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
        Router router = new Router(String.format(CommandPath.SUCCESS_PAGE, req.getContextPath()), RouterType.REDIRECT);
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put(RequestParam.CARD_ID, req.getParameter(RequestParam.CARD_ID));
        paymentData.put(RequestParam.CVC, req.getParameter(RequestParam.CVC));
        paymentData.put(RequestParam.CARD_NUMBER, req.getParameter(RequestParam.CARD_NUMBER));
        paymentData.put(RequestParam.AMOUNT, req.getParameter(RequestParam.AMOUNT));
        paymentData.put(RequestParam.VALID_THRU, req.getParameter(RequestParam.VALID_THRU));
        try {
            if (!paymentService.makePayment(paymentData)) {
                req.setAttribute(AttributeKey.ERRORS, paymentData);
                router.setForward(CommandPath.CARD_PAGE);
            }
        } catch (ServiceException e) {
            log.error(e);
            router.setForward(JspPath.ERROR_500);
        }
        return router;
    }
}
