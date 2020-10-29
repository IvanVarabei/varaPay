package com.varabei.ivan.controller.command.impl;

import com.varabei.ivan.controller.JspPath;
import com.varabei.ivan.controller.RequestParam;
import com.varabei.ivan.controller.command.ActionCommand;
import com.varabei.ivan.controller.router.Router;
import com.varabei.ivan.model.entity.User;
import com.varabei.ivan.model.exception.ServiceException;
import com.varabei.ivan.model.service.MailService;
import com.varabei.ivan.model.service.ServiceFactory;
import com.varabei.ivan.model.service.UserService;
import com.varabei.ivan.util.CustomSecurity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CreateCardCommand implements ActionCommand {
    private static final Logger log = LogManager.getLogger(CreateCardCommand.class);
    private static UserService userService = ServiceFactory.getInstance().getUserService();
    private static MailService mailService = ServiceFactory.getInstance().getMailService();
    private static final String MAIL_SUBJECT = "Create new card";
    private static final String MAIL_BODY = "Confirm create card action. Your code is %s.";
    private static final int TEMP_CODE_LENGTH = 4;

    @Override
    public Router execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Router router = new Router(JspPath.VERIFY_CREATE_CARD);
        Long accountId = Long.parseLong(req.getParameter(RequestParam.ACCOUNT_ID));
        try {
            String login = req.getSession().getAttribute(RequestParam.LOGIN).toString();
            String tempCode = CustomSecurity.generateRandom(TEMP_CODE_LENGTH);
            User user = userService.findByLogin(login).get();
            mailService.sendEmail(user.getEmail(), MAIL_SUBJECT, String.format(MAIL_BODY, tempCode));
            req.getSession().setAttribute(RequestParam.TEMP_CODE, tempCode);
            req.getSession().setAttribute(RequestParam.ACCOUNT_ID, accountId);
        } catch (ServiceException e) {
            log.error(e);
            router.setForward(JspPath.ERROR_500);
        }
        return router;
    }
}
