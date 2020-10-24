package com.varabei.ivan.controller.command;

public class RedirectPath {
    public static final String RUN_BIDS = "%s/mainServlet?command=RUN_BIDS_GET&&page=%s";
    public static final String RUN_ACCOUNTS = "%s/mainServlet?command=run_accounts_get";
    public static final String PROFILE = "%s/mainServlet?command=profile_get";
    public static final String CHANGING_PASSWORD = "%s/mainServlet?command=profile_get";
    public static final String SUCCESS_PAGE = "%s/mainServlet?command=success_get";
    public static final String LOGIN = "%s/mainServlet?command=login_get";

    private RedirectPath(){
    }
}
