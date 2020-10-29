package com.varabei.ivan.controller.command.impl;

import com.varabei.ivan.controller.AttributeKey;
import com.varabei.ivan.controller.JspPath;
import com.varabei.ivan.controller.RedirectPath;
import com.varabei.ivan.controller.RequestParam;
import com.varabei.ivan.controller.command.ActionCommand;
import com.varabei.ivan.controller.router.Router;
import com.varabei.ivan.model.exception.ServiceException;
import com.varabei.ivan.model.service.ServiceFactory;
import com.varabei.ivan.model.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ChangePasswordCommand implements ActionCommand {
    private static final Logger log = LogManager.getLogger(ChangePasswordCommand.class);
    private static UserService userService = ServiceFactory.getInstance().getUserService();

    @Override
    public Router execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Router router = new Router(JspPath.CHANGE_PASSWORD);
        Map<String, String> changePasswordData = new HashMap<>();
        changePasswordData.put(RequestParam.OLD_PASSWORD, req.getParameter(RequestParam.OLD_PASSWORD));
        changePasswordData.put(RequestParam.PASSWORD, req.getParameter(RequestParam.PASSWORD));
        changePasswordData.put(RequestParam.REPEAT_PASSWORD, req.getParameter(RequestParam.REPEAT_PASSWORD));
        changePasswordData.put(RequestParam.LOGIN, req.getSession().getAttribute(RequestParam.LOGIN).toString());
        try {
            if (userService.updatePassword(changePasswordData)) {
                router.setRedirect(String.format(RedirectPath.CHANGING_PASSWORD, req.getContextPath()));
            } else {
                req.setAttribute(AttributeKey.ERRORS, changePasswordData);
            }
        } catch (ServiceException e) {
            log.error(e);
            router.setForward(JspPath.ERROR_500);
        }
        return router;
    }
}
