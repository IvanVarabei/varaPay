package com.varabei.ivan.controller.command.impl;

import com.varabei.ivan.Const;
import com.varabei.ivan.controller.command.ActionCommand;
import com.varabei.ivan.model.entity.User;
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

public class CreateCardCommand implements ActionCommand {
    private static final Logger log = LogManager.getLogger(CreateCardCommand.class);
    private static final UserService userService = ServiceFactory.getInstance().getUserService();
    private static final MailService mailService = ServiceFactory.getInstance().getMailService();
    private static final String JSP_VERIFY_CREATE_CARD = "/WEB-INF/pages/verifyCreateCard.jsp";
    private static final String MAIL_SUBJECT = "Create new card";
    private static final String MAIL_BODY = "Confirm create card action. Your code is %s.";
    private static final int TEMP_CODE_LENGTH = 4;

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Long accountId = Long.parseLong(req.getParameter(Const.AccountField.ID));
        try {
            Long userId = Long.parseLong(req.getSession().getAttribute(Const.UserField.ID).toString());
            String tempCode = CustomSecurity.generateRandom(TEMP_CODE_LENGTH);
            User user = userService.findById(userId).get();
            mailService.sendEmail(user.getEmail(), MAIL_SUBJECT, String.format(MAIL_BODY, tempCode));
            req.getSession().setAttribute(Const.RequestParam.TEMP_CODE, tempCode);
            req.getSession().setAttribute(Const.AccountField.ID, accountId);
            req.getRequestDispatcher(JSP_VERIFY_CREATE_CARD).forward(req, resp);
        } catch (ServiceException e) {
            log.error(e);
            resp.sendError(Const.ErrorInfo.SERVER_ERROR_CODE);
        }
    }
}
