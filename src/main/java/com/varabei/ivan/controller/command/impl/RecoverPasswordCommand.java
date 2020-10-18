package com.varabei.ivan.controller.command.impl;

import com.varabei.ivan.Const;
import com.varabei.ivan.controller.command.ActionCommand;
import com.varabei.ivan.model.exception.ServiceException;
import com.varabei.ivan.model.service.MailService;
import com.varabei.ivan.model.service.ServiceFactory;
import com.varabei.ivan.model.service.UserService;
import com.varabei.ivan.util.MathUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RecoverPasswordCommand implements ActionCommand {
    private static final Logger log = LogManager.getLogger(RecoverPasswordCommand.class);
    private static final MailService mailService = ServiceFactory.getInstance().getMailService();
    private static final UserService userService = ServiceFactory.getInstance().getUserService();
    private static final String MAIL_SUBJECT_NEW_PASSWORD = "Your new password";
    private static final String MAIL_CONTENT = "Hi! Your new password is : %s. " +
            "You can change password in your profile.";
    private static final int DEFAULT_PASSWORD_LENGTH = 10;

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String email = req.getParameter(Const.UserField.EMAIL);
        String newPassword = MathUtil.generateRandom(DEFAULT_PASSWORD_LENGTH);
        try {
            mailService.sendEmail(email, MAIL_SUBJECT_NEW_PASSWORD, String.format(MAIL_CONTENT, newPassword));
            userService.updatePassword(email, newPassword);
        } catch (ServiceException e) {
            log.error(e);
            resp.sendError(Const.ErrorInfo.SERVER_ERROR_CODE);
        }
    }
}
