package com.epam.varapay.controller.command.impl;

import com.epam.varapay.controller.CommandPath;
import com.epam.varapay.controller.JspPath;
import com.epam.varapay.controller.RequestParam;
import com.epam.varapay.controller.command.ActionCommand;
import com.epam.varapay.controller.router.Router;
import com.epam.varapay.model.entity.User;
import com.epam.varapay.exception.ServiceException;
import com.epam.varapay.model.service.ServiceFactory;
import com.epam.varapay.model.service.UserService;
import com.epam.varapay.util.CustomSecurity;
import com.epam.varapay.util.MailSender;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CreateCardCommand implements ActionCommand {
    private static final Logger log = LogManager.getLogger(CreateCardCommand.class);
    private static UserService userService = ServiceFactory.getInstance().getUserService();
    private static MailSender mailSender = MailSender.getInstance();
    private static final String MAIL_SUBJECT = "Create new card";
    private static final String MAIL_BODY = "Confirm create card action. Your code is %s.";
    private static final int TEMP_CODE_LENGTH = 4;

    @Override
    public Router execute(HttpServletRequest req, HttpServletResponse resp) {
        Router router = new Router();
        Long accountId = Long.parseLong(req.getParameter(RequestParam.ACCOUNT_ID));
        try {
            String login = req.getSession().getAttribute(RequestParam.LOGIN).toString();
            String tempCode = CustomSecurity.generateRandom(TEMP_CODE_LENGTH);
            User user = userService.findByLogin(login).get();
            mailSender.sendEmail(user.getEmail(), MAIL_SUBJECT, String.format(MAIL_BODY, tempCode));
            req.getSession().setAttribute(RequestParam.TEMP_CODE, tempCode);
            req.getSession().setAttribute(RequestParam.ACCOUNT_ID, accountId);
            router.setRedirect(String.format(CommandPath.VERIFY_CREATE_CARD, req.getContextPath()));
        } catch (ServiceException e) {
            log.error(e);
            router.setForward(JspPath.ERROR_500);
        }
        return router;
    }
}
