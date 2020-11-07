package com.epam.varapay.controller.filter;

import com.epam.varapay.controller.AttributeKey;
import com.epam.varapay.controller.CommandPath;
import com.epam.varapay.controller.JspPath;
import com.epam.varapay.controller.RequestParam;
import com.epam.varapay.controller.command.CommandRight;
import com.epam.varapay.controller.command.CommandType;
import com.epam.varapay.model.entity.Role;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class SecurityFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession();
        Object userRole = session.getAttribute(AttributeKey.ROLE_NAME);
        String command = req.getParameter(RequestParam.COMMAND);
        List<Role> allowedRoles = null;
        if (command != null && !command.isEmpty()) {
            allowedRoles = CommandRight.commandRoles.get(CommandType.valueOf(command.toUpperCase()));
        }
        if (userRole == null && allowedRoles != null) {
            resp.sendRedirect(String.format(CommandPath.LOGIN, req.getContextPath()));
        } else {
            if (allowedRoles == null || allowedRoles.contains(Role.valueOf(userRole.toString().toUpperCase()))) {
                chain.doFilter(req, resp);
            } else {
                req.getRequestDispatcher(JspPath.ACCESS_DENIED).forward(req, resp);
            }
        }
    }

    @Override
    public void destroy() {
    }
}
