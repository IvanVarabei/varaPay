package com.varabei.ivan.controller.command.impl;

import com.varabei.ivan.controller.AttributeKey;
import com.varabei.ivan.controller.CommandPath;
import com.varabei.ivan.controller.JspPath;
import com.varabei.ivan.controller.RequestParam;
import com.varabei.ivan.controller.command.ActionCommand;
import com.varabei.ivan.controller.router.Router;
import com.varabei.ivan.model.entity.User;
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
import java.util.Optional;

public class VerifyEmailCommand implements ActionCommand {
    private static final Logger log = LogManager.getLogger(VerifyEmailCommand.class);
    private static UserService userService = ServiceFactory.getInstance().getUserService();

    @Override
    public Router execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Router router = new Router(JspPath.VERIFY_EMAIL);
        HttpSession session = req.getSession();
        String tempCode = req.getParameter(RequestParam.TEMP_CODE);
        String originTempCode = session.getAttribute(RequestParam.TEMP_CODE).toString();
        String password = session.getAttribute(RequestParam.PASSWORD).toString();
        String secretWord = session.getAttribute(RequestParam.SECRET_WORD).toString();
        User user = (User) session.getAttribute(AttributeKey.USER);
        try {
            Optional<String> error = userService.signUp(user, password, secretWord, tempCode, originTempCode);
            if (error.isEmpty()) {
                router.setRedirect(String.format(CommandPath.SUCCESS_PAGE, req.getContextPath()));
            } else {
                req.setAttribute(AttributeKey.ERROR, error.get());
            }
        } catch (ServiceException e) {
            log.error(e);
            router.setForward(JspPath.ERROR_500);
        }
        return router;
    }
}
