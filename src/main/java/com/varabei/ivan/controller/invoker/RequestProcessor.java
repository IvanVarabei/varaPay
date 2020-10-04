package com.varabei.ivan.controller.invoker;

import com.varabei.ivan.Const;
import com.varabei.ivan.controller.command.ActionCommand;
import com.varabei.ivan.controller.command.provider.ActionProvider;
import com.varabei.ivan.model.dao.pool.ConnectionPool;
import com.varabei.ivan.model.entity.User;
import com.varabei.ivan.model.service.ServiceException;
import com.varabei.ivan.model.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;

public class RequestProcessor extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ActionCommand actionCommand = ActionProvider.defineAction(req.getParameter(Const.RequestParam.COMMAND));
        actionCommand.execute(req, resp);
    }

    @Override
    public void destroy() {
        ConnectionPool.getInstance().destroyPool();
    }
}
