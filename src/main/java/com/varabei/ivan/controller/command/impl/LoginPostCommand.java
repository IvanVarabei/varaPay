package com.varabei.ivan.controller.command.impl;

import com.varabei.ivan.Const;
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
import java.util.Optional;

public class LoginPostCommand implements ActionCommand {
    private static final UserService userService = ServiceFactory.getInstance().getUserService();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String login = req.getParameter(Const.UserField.LOGIN);
        String password = req.getParameter(Const.UserField.PASSWORD);
        HttpSession session = req.getSession();
        try {
            Optional<User> user = userService.signIn(login, password);
            if(user.isPresent()){
                session.setAttribute(Const.UserField.ROLE_NAME, user.get().getRoleName());
                session.setAttribute(Const.UserField.ID, user.get().getId());
                resp.sendRedirect(req.getContextPath()+ "/mainServlet?command=profile");
            }else{
                req.setAttribute(Const.AttributeKey.ERROR, Const.ErrorInfo.WRONG_LOGIN_OR_PASSWORD);
                req.getRequestDispatcher("/mainServlet?command=login_get").forward(req, resp);
            }
        } catch (ServiceException e) {

        }
    }
}
