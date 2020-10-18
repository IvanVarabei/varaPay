package com.varabei.ivan.controller.command.impl;

import com.varabei.ivan.Const;
import com.varabei.ivan.controller.command.ActionCommand;
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

public class ProfileCommand implements ActionCommand {
    private static final Logger log = LogManager.getLogger(ProfileCommand.class);
    private static final UserService userService = ServiceFactory.getInstance().getUserService();
    private static final String JSP_PROFILE = "/WEB-INF/pages/profile.jsp";

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        HttpSession session = req.getSession();
        Long userId = (Long) session.getAttribute(Const.UserField.ID);
        try {
            req.setAttribute(Const.AttributeKey.USER, userService.findById(userId).orElse(null));
            req.getRequestDispatcher(JSP_PROFILE).forward(req, resp);
        } catch (ServiceException e) {
            log.error(e);
            resp.sendError(Const.ErrorInfo.SERVER_ERROR_CODE);
        }
    }
}
