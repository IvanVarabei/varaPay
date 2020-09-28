package com.varabei.ivan.controller.invoker;

import com.varabei.ivan.controller.command.ActionCommand;
import com.varabei.ivan.controller.command.provider.ActionProvider;
import com.varabei.ivan.model.dao.DaoException;
import com.varabei.ivan.model.dao.impl.DbUserDao;
import com.varabei.ivan.model.dao.pool.ConnectionPool;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class RequestProcessor extends HttpServlet {
    public Map<String, Object> doRequest(String action, Map<String, Object> params) {
        ActionCommand actionCommand = ActionProvider.defineAction(action);
        return actionCommand.execute(params);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int i = new DbUserDao().findAll().size();
            req.setAttribute("userAmount", i);
        } catch (DaoException e) {
            e.printStackTrace();
        }
        req.getRequestDispatcher("/WEB-INF/pages/main.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    public void destroy() {
        ConnectionPool.getInstance().destroyPool();
    }
}
