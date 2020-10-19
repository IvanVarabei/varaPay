package com.varabei.ivan.controller.command.impl;

import com.varabei.ivan.Const;
import com.varabei.ivan.controller.command.ActionCommand;
import com.varabei.ivan.model.exception.ServiceException;
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

public class MakePaymentCommand implements ActionCommand {
    private static final Logger log = LogManager.getLogger(MakePaymentCommand.class);
    private static final String REDIRECT_SUCCESS_PAGE = "%s/mainServlet?command=success_get";
    private static final PaymentService paymentService = ServiceFactory.getInstance().getPaymentService();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String s = req.getParameter(Const.CardField.ID);
        Long sourceCardId = Long.parseLong(s);
        String destinationCardNumber = req.getParameter(Const.RequestParam.DESTINATION_CARD_NUMBER);
        String sourceCardCvc = req.getParameter(Const.CardField.CVC);
        BigDecimal amount = BigDecimal.valueOf(Double.parseDouble(req.getParameter(Const.PaymentField.AMOUNT)));
        YearMonth destinationCardValidThru = YearMonth.parse(req.getParameter(Const.RequestParam.DESTINATION_CARD_VALID_THRU));
        try {
            paymentService.makePayment(sourceCardId, sourceCardCvc,
                    destinationCardNumber, destinationCardValidThru, amount);
            resp.sendRedirect(String.format(REDIRECT_SUCCESS_PAGE, req.getContextPath()));
        } catch (ServiceException e) {
            log.error(e);
            resp.sendError(Const.ErrorInfo.SERVER_ERROR_CODE);
        }
    }
}
