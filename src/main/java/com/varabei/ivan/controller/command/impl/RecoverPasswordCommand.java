package com.varabei.ivan.controller.command.impl;

import com.varabei.ivan.common.ErrorInfo;
import com.varabei.ivan.controller.AttributeKey;
import com.varabei.ivan.controller.JspPath;
import com.varabei.ivan.controller.RedirectPath;
import com.varabei.ivan.controller.RequestParam;
import com.varabei.ivan.controller.command.ActionCommand;
import com.varabei.ivan.controller.router.Router;
import com.varabei.ivan.controller.router.RouterType;
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

public class RecoverPasswordCommand implements ActionCommand {
    private static final Logger log = LogManager.getLogger(RecoverPasswordCommand.class);
    private static MailService mailService = ServiceFactory.getInstance().getMailService();
    private static UserService userService = ServiceFactory.getInstance().getUserService();
    private static final String MAIL_SUBJECT_NEW_PASSWORD = "Your new password";
    private static final String MAIL_CONTENT = "Hi! Your new password is : %s. " +
            "You can change password in your profile.";
    private static final int DEFAULT_PASSWORD_LENGTH = 20;

    @Override
    public Router execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Router router = new Router(String.format(RedirectPath.LOGIN, req.getContextPath()), RouterType.REDIRECT);
        String email = req.getParameter(RequestParam.EMAIL);
        String newPassword = CustomSecurity.generateRandom(DEFAULT_PASSWORD_LENGTH);
        try {
            if (userService.findByEmail(email).isPresent()) {
                mailService.sendEmail(email, MAIL_SUBJECT_NEW_PASSWORD, String.format(MAIL_CONTENT, newPassword));
                userService.updatePassword(email, newPassword);
            } else {
                req.setAttribute(AttributeKey.ERROR, ErrorInfo.EMAIL_DOES_NOT_EXISTS);
                router.setForward(JspPath.RECOVER_PASSWORD);
            }
        } catch (ServiceException e) {
            log.error(e);
            router.setForward(JspPath.ERROR_500);
        }
        return router;
    }
}
