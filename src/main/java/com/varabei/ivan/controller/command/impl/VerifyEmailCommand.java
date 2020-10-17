package com.varabei.ivan.controller.command.impl;

import com.varabei.ivan.controller.command.ActionCommand;
import com.varabei.ivan.model.entity.User;
import com.varabei.ivan.model.exception.ServiceException;
import com.varabei.ivan.model.service.ServiceFactory;
import com.varabei.ivan.model.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class VerifyEmailCommand implements ActionCommand {
    private static final UserService userService = ServiceFactory.getInstance().getUserService();
    private static final String FORWARD_VERIFY_EMAIL_PAGE_GET = "/WEB-INF/pages/verifyEmail.jsp";

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        HttpSession session = req.getSession();
        String tempPassword = req.getParameter("tempPassword");
        if (tempPassword.equals(session.getAttribute("tempPassword"))) {
            try {
                User user = (User) session.getAttribute("user");
                if (userService.findByLogin(user.getLogin()).isPresent()) {
                    req.setAttribute("error", "During your registration an another user took '%s' login. Repeat registration from scratch.");
                    req.getRequestDispatcher(FORWARD_VERIFY_EMAIL_PAGE_GET).forward(req, resp);
                } else {
                    userService.signUp(user);
                }
                resp.sendRedirect(req.getContextPath());
            } catch (ServiceException e) {
                e.printStackTrace();
            }
        } else {
            req.setAttribute("error", "wrong");
            req.getRequestDispatcher(FORWARD_VERIFY_EMAIL_PAGE_GET).forward(req, resp);
        }
    }
}
