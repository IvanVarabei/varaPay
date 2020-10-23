package com.varabei.ivan.controller.filter;

import com.varabei.ivan.controller.RequestParam;
import com.varabei.ivan.controller.command.client.CommandType;
import com.varabei.ivan.model.entity.name.UserField;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PermissionFilter implements Filter {
    private static final Map<String, List<String>> commandNamePermittedRoles = new HashMap<>();
    private static final String JSP_WELCOME = "/WEB-INF/pages/welcome.jsp";
    private static final String REDIRECT_TO_LOGIN = "%s/mainServlet?command=login_get";

    @Override
    public void init(FilterConfig filterConfig) {
        List<String> adminClient = List.of("админ", "клиент");
        List<String> admin = List.of("админ");
        commandNamePermittedRoles.put(CommandType.PROFILE_GET.name().toLowerCase(), adminClient);
        commandNamePermittedRoles.put(CommandType.RUN_BIDS_GET.name(), admin);
    }

//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//        HttpServletRequest req = (HttpServletRequest) request;
//        HttpServletResponse resp = (HttpServletResponse) response;
//        HttpSession session = req.getSession();
//        String command = req.getParameter(RequestParam.COMMAND);
//        List<String> allowedRoles = commandNamePermittedRoles.get(command);
//        Object userRole = session.getAttribute(UserField.ROLE_NAME);
//        if (userRole == null && allowedRoles != null) {
//            resp.sendRedirect(String.format(REDIRECT_TO_LOGIN, req.getContextPath()));
//        } else {
//            if (allowedRoles == null || allowedRoles.contains(userRole)) {
//                chain.doFilter(req, resp);
//            } else {
//                req.getRequestDispatcher(JSP_WELCOME).forward(req, resp);
//            }
//        }
//    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession();
        String command = req.getParameter(RequestParam.COMMAND);
        List<String> allowedRoles = commandNamePermittedRoles.get(command);
        Object userRole = session.getAttribute(UserField.ROLE_NAME);
//        if (userRole == null && allowedRoles != null) {
//            resp.sendRedirect(String.format(REDIRECT_TO_LOGIN, req.getContextPath()));
//        } else {
           //if (allowedRoles == null || allowedRoles.contains(userRole)) {
                chain.doFilter(req, resp);
//            } else {
//                req.getRequestDispatcher(JSP_WELCOME).forward(req, resp);
//            }

    }

    @Override
    public void destroy() {
    }
}
