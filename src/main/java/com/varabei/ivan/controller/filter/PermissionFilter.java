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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PermissionFilter implements Filter {
    private static final Map<String, List<Role>> commandNamePermittedRoles = new HashMap<>();

    @Override
    public void init(FilterConfig filterConfig) {
        List<Role> adminClient = List.of(Role.ADMIN, Role.CLIENT);
        List<Role> admin = List.of(Role.ADMIN);
        List<Role> client = List.of(Role.CLIENT);
        commandNamePermittedRoles.put(CommandType.PROFILE_GET.name().toLowerCase(), adminClient);
        commandNamePermittedRoles.put(CommandType.RECOVER_PASSWORD_GET.name().toLowerCase(), adminClient);
        commandNamePermittedRoles.put(CommandType.RECOVER_PASSWORD_POST.name().toLowerCase(), adminClient);
        commandNamePermittedRoles.put(CommandType.CHANGE_PASSWORD_GET.name().toLowerCase(), adminClient);
        commandNamePermittedRoles.put(CommandType.CHANGE_PASSWORD_POST.name().toLowerCase(), adminClient);
        commandNamePermittedRoles.put(CommandType.SUCCESS_GET.name().toLowerCase(), adminClient);

        commandNamePermittedRoles.put(CommandType.CARD_PAGE_GET.name().toLowerCase(), client);
        commandNamePermittedRoles.put(CommandType.MAKE_PAYMENT_POST.name().toLowerCase(), client);
        commandNamePermittedRoles.put(CommandType.BLOCK_ACCOUNT_POST.name().toLowerCase(), client);
        commandNamePermittedRoles.put(CommandType.PLACE_TOP_UP_BID_POST.name().toLowerCase(), client);
        commandNamePermittedRoles.put(CommandType.PLACE_WITHDRAW_BID_POST.name().toLowerCase(), client);
        commandNamePermittedRoles.put(CommandType.DELETE_ACCOUNT_POST.name().toLowerCase(), client);
        commandNamePermittedRoles.put(CommandType.CREATE_ACCOUNT_POST.name().toLowerCase(), client);
        commandNamePermittedRoles.put(CommandType.DELETE_CARD_POST.name().toLowerCase(), client);
        commandNamePermittedRoles.put(CommandType.VERIFY_CREATE_CARD_POST.name().toLowerCase(), client);
        commandNamePermittedRoles.put(CommandType.CREATE_CARD_POST.name().toLowerCase(), client);
        commandNamePermittedRoles.put(CommandType.ACCOUNT_PAGE_GET.name().toLowerCase(), client);
        commandNamePermittedRoles.put(CommandType.TOP_UP_AMOUNT_PAGE_GET.name().toLowerCase(), client);
        commandNamePermittedRoles.put(CommandType.TOP_UP_PAGE_GET.name().toLowerCase(), client);

        commandNamePermittedRoles.put(CommandType.RUN_ACCOUNTS_GET.name().toLowerCase(), admin);
        commandNamePermittedRoles.put(CommandType.RUN_BIDS_GET.name().toLowerCase(), admin);
        commandNamePermittedRoles.put(CommandType.APPROVE_TOP_UP_BID_POST.name().toLowerCase(), admin);
        commandNamePermittedRoles.put(CommandType.APPROVE_WITHDRAW_BID_POST.name().toLowerCase(), admin);
        commandNamePermittedRoles.put(CommandType.REJECT_TOP_UP_BID_POST.name().toLowerCase(), admin);
        commandNamePermittedRoles.put(CommandType.REJECT_WITHDRAW_BID_POST.name().toLowerCase(), admin);
        commandNamePermittedRoles.put(CommandType.UNBLOCK_ACCOUNT_POST.name().toLowerCase(), admin);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession();
        String command = req.getParameter(RequestParam.COMMAND);
        List<Role> allowedRoles = commandNamePermittedRoles.get(command);
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
