package com.varabei.ivan.controller.filter;

import com.varabei.ivan.controller.JspPath;
import com.varabei.ivan.controller.RedirectPath;
import com.varabei.ivan.controller.RequestParam;
import com.varabei.ivan.controller.command.client.CommandType;
import com.varabei.ivan.model.entity.Role;
import com.varabei.ivan.model.entity.name.UserField;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.EnumMap;
import java.util.List;

public class PermissionFilter implements Filter {
    private static final EnumMap<CommandType, List<Role>> commandNamePermittedRoles = new EnumMap<>(CommandType.class);

    @Override
    public void init(FilterConfig filterConfig) {
        List<Role> adminClient = List.of(Role.ADMIN, Role.CLIENT);
        List<Role> admin = List.of(Role.ADMIN);
        List<Role> client = List.of(Role.CLIENT);
        commandNamePermittedRoles.put(CommandType.PROFILE_GET, adminClient);
        commandNamePermittedRoles.put(CommandType.RECOVER_PASSWORD_GET, adminClient);
        commandNamePermittedRoles.put(CommandType.RECOVER_PASSWORD_POST, adminClient);
        commandNamePermittedRoles.put(CommandType.CHANGE_PASSWORD_GET, adminClient);
        commandNamePermittedRoles.put(CommandType.CHANGE_PASSWORD_POST, adminClient);
        commandNamePermittedRoles.put(CommandType.SUCCESS_GET, adminClient);

        commandNamePermittedRoles.put(CommandType.CARD_PAGE_GET, client);
        commandNamePermittedRoles.put(CommandType.MAKE_PAYMENT_POST, client);
        commandNamePermittedRoles.put(CommandType.BLOCK_ACCOUNT_POST, client);
        commandNamePermittedRoles.put(CommandType.PLACE_TOP_UP_BID_POST, client);
        commandNamePermittedRoles.put(CommandType.PLACE_WITHDRAW_BID_POST, client);
        commandNamePermittedRoles.put(CommandType.DELETE_ACCOUNT_POST, client);
        commandNamePermittedRoles.put(CommandType.CREATE_ACCOUNT_POST, client);
        commandNamePermittedRoles.put(CommandType.DELETE_CARD_POST, client);
        commandNamePermittedRoles.put(CommandType.VERIFY_CREATE_CARD_POST, client);
        commandNamePermittedRoles.put(CommandType.CREATE_CARD_POST, client);
        commandNamePermittedRoles.put(CommandType.ACCOUNT_PAGE_GET, client);
        commandNamePermittedRoles.put(CommandType.TOP_UP_AMOUNT_PAGE_GET, client);
        commandNamePermittedRoles.put(CommandType.TOP_UP_PAGE_GET, client);

        commandNamePermittedRoles.put(CommandType.RUN_ACCOUNTS_GET, admin);
        commandNamePermittedRoles.put(CommandType.RUN_BIDS_GET, admin);
        commandNamePermittedRoles.put(CommandType.APPROVE_TOP_UP_BID_POST, admin);
        commandNamePermittedRoles.put(CommandType.APPROVE_WITHDRAW_BID_POST, admin);
        commandNamePermittedRoles.put(CommandType.REJECT_TOP_UP_BID_POST, admin);
        commandNamePermittedRoles.put(CommandType.REJECT_WITHDRAW_BID_POST, admin);
        commandNamePermittedRoles.put(CommandType.UNBLOCK_ACCOUNT_POST, admin);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession();
        String command = req.getParameter(RequestParam.COMMAND);
        List<Role> allowedRoles = null;
        if(command != null && !command.isEmpty()){
            allowedRoles = commandNamePermittedRoles.get(CommandType.valueOf(command.toUpperCase()));
        }
        Object userRole = session.getAttribute(UserField.ROLE_NAME);
        if (userRole == null && allowedRoles != null) {
            resp.sendRedirect(String.format(RedirectPath.LOGIN, req.getContextPath()));
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
