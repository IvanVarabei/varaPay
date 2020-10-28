package com.varabei.ivan.controller.command.impl;

import com.varabei.ivan.common.ErrorInfo;
import com.varabei.ivan.controller.AttributeKey;
import com.varabei.ivan.controller.JspPath;
import com.varabei.ivan.controller.RedirectPath;
import com.varabei.ivan.controller.RequestParam;
import com.varabei.ivan.controller.command.ActionCommand;
import com.varabei.ivan.controller.router.Router;
import com.varabei.ivan.model.entity.User;
import com.varabei.ivan.model.entity.name.UserField;
import com.varabei.ivan.model.exception.ServiceException;
import com.varabei.ivan.model.service.ServiceFactory;
import com.varabei.ivan.model.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class VerifyEmailCommand implements ActionCommand {
    private static final Logger log = LogManager.getLogger(VerifyEmailCommand.class);
    private static UserService userService = ServiceFactory.getInstance().getUserService();

    @Override
    public Router execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Router router = new Router(JspPath.VERIFY_EMAIL);
        HttpSession session = req.getSession();
        String tempCode = req.getParameter(RequestParam.TEMP_CODE);
        if (tempCode.equals(session.getAttribute(RequestParam.TEMP_CODE).toString())) {
            try {
                User user = (User) session.getAttribute(AttributeKey.USER);
                String login = user.getLogin();
                if (userService.findByLogin(login).isPresent()) {
                    req.setAttribute(AttributeKey.ERROR, String.format(ErrorInfo.LOGIN_TAKEN.name().toLowerCase(), login));
                } else {
                    userService.signUp(user, session.getAttribute(UserField.PASSWORD).toString()
                            , session.getAttribute(UserField.SECRET_WORD).toString());
                    router.setRedirect(String.format(RedirectPath.SUCCESS_PAGE, req.getContextPath()));
                }
            } catch (ServiceException e) {
                log.error(e);
                router.setForward(JspPath.ERROR_500);
            }
        } else {
            req.setAttribute(AttributeKey.ERROR, ErrorInfo.TEMP_CODE);
            router.setForward(JspPath.ERROR_500);
        }
        return router;
    }
}
