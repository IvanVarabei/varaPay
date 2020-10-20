package com.varabei.ivan.controller.command.impl;

import com.varabei.ivan.common.ErrorInfo;
import com.varabei.ivan.controller.command.ActionCommand;
import com.varabei.ivan.model.entity.name.AccountField;
import com.varabei.ivan.model.exception.ServiceException;
import com.varabei.ivan.model.service.AccountService;
import com.varabei.ivan.model.service.ServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class BlockAccountCommand implements ActionCommand {
    private static final Logger log = LogManager.getLogger(BlockAccountCommand.class);
    private static final AccountService accountService = ServiceFactory.getInstance().getAccountService();
    private static final String REDIRECT_AFTER_BLOCKING = "%s/mainServlet?command=profile_get";

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Long accountId = Long.parseLong(req.getParameter(AccountField.ID));
        try {
            accountService.changeActive(accountId);
            resp.sendRedirect(String.format(REDIRECT_AFTER_BLOCKING, req.getContextPath()));
        } catch (ServiceException e) {
            log.error(e);
            resp.sendError(ErrorInfo.SERVER_ERROR_CODE);
        }
    }
}
