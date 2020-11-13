package com.epam.varapay.controller.command.impl;

import com.epam.varapay.controller.AttributeKey;
import com.epam.varapay.controller.CommandPath;
import com.epam.varapay.controller.JspPath;
import com.epam.varapay.controller.RequestParam;
import com.epam.varapay.controller.command.ActionCommand;
import com.epam.varapay.controller.router.Router;
import com.epam.varapay.controller.router.RouterType;
import com.epam.varapay.model.entity.User;
import com.epam.varapay.exception.ServiceException;
import com.epam.varapay.model.service.ServiceFactory;
import com.epam.varapay.model.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class SignupCommand implements ActionCommand {
    private static final Logger log = LogManager.getLogger(SignupCommand.class);
    private static UserService userService = ServiceFactory.getInstance().getUserService();

    @Override
    public Router execute(HttpServletRequest req, HttpServletResponse resp) {
        Router router = new Router(String.format(CommandPath.VERIFY_EMAIL, req.getContextPath()), RouterType.REDIRECT);
        Map<String, String> signupData = new HashMap<>();
        signupData.put(RequestParam.LOGIN, req.getParameter(RequestParam.LOGIN));
        signupData.put(RequestParam.PASSWORD, req.getParameter(RequestParam.PASSWORD));
        signupData.put(RequestParam.REPEAT_PASSWORD, req.getParameter(RequestParam.REPEAT_PASSWORD));
        signupData.put(RequestParam.FIRST_NAME, req.getParameter(RequestParam.FIRST_NAME));
        signupData.put(RequestParam.LAST_NAME, req.getParameter(RequestParam.LAST_NAME));
        signupData.put(RequestParam.EMAIL, req.getParameter(RequestParam.EMAIL));
        signupData.put(RequestParam.BIRTH, req.getParameter(RequestParam.BIRTH));
        signupData.put(RequestParam.SECRET_WORD, req.getParameter(RequestParam.SECRET_WORD));
        try {
            Optional<String> tempCode = userService.checkSignupDataAndSendEmail(signupData);
            if (tempCode.isPresent()) {
                req.getSession().setAttribute(RequestParam.TEMP_CODE, tempCode.get());
                req.getSession().setAttribute(RequestParam.PASSWORD, signupData.get(RequestParam.PASSWORD));
                req.getSession().setAttribute(RequestParam.SECRET_WORD, signupData.get(RequestParam.SECRET_WORD));
                req.getSession().setAttribute(AttributeKey.USER, new User(signupData.get(RequestParam.LOGIN),
                        signupData.get(RequestParam.FIRST_NAME), signupData.get(RequestParam.LAST_NAME),
                        signupData.get(RequestParam.EMAIL), LocalDate.parse(signupData.get(RequestParam.BIRTH))));
            } else {
                req.setAttribute(AttributeKey.ERRORS, signupData);
                router.setForward(JspPath.SIGNUP);
            }
        } catch (ServiceException e) {
            log.error(e);
            router.setForward(JspPath.ERROR_500);
        }
        return router;
    }
}
