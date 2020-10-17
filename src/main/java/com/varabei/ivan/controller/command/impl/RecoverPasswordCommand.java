package com.varabei.ivan.controller.command.impl;

import com.varabei.ivan.controller.command.ActionCommand;
import com.varabei.ivan.model.exception.ServiceException;
import com.varabei.ivan.model.service.MailService;
import com.varabei.ivan.model.service.ServiceFactory;
import com.varabei.ivan.model.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RecoverPasswordCommand implements ActionCommand {
    MailService mailService = ServiceFactory.getInstance().getMailService();
    UserService userService = ServiceFactory.getInstance().getUserService();
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String email = req.getParameter("email");
        String newPassword = "1234";
        try {
            mailService.recoverPassword(email, newPassword);
            userService.updatePassword(email, newPassword);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }
}
