package com.varabei.ivan.controller;

public class CommandPath {
    public static final String RUN_BIDS = "%s/mainServlet?command=RUN_BIDS_GET&&page=%s";
    public static final String RUN_ACCOUNTS = "%s/mainServlet?command=run_accounts_get&query=%s";
    public static final String PROFILE = "%s/mainServlet?command=profile_get";
    public static final String CHANGING_PASSWORD = "%s/mainServlet?command=profile_get";
    public static final String SUCCESS_PAGE = "%s/mainServlet?command=success_get";
    public static final String LOGIN = "%s/mainServlet?command=login_get";
    public static final String ACCOUNT = "%s/mainServlet?command=account_page_get&account_id=%s";
    public static final String VERIFY_CREATE_CARD = "%s/mainServlet?command=verify_create_card_get";
    public static final String VERIFY_EMAIL = "%s/mainServlet?command=verify_email_get";
    public static final String CARD_PAGE = "/mainServlet?command=card_page_get";
    public static final String TOP_UP_PAGE = "/mainServlet?command=top_up_page_get";

    private CommandPath(){
    }
}
