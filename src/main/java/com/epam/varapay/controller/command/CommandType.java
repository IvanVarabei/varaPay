package com.epam.varapay.controller.command;

import com.epam.varapay.controller.JspPath;
import com.epam.varapay.controller.command.impl.*;
import com.epam.varapay.controller.router.Router;

public enum CommandType {
    WELCOME_GET((req, resp) -> new Router(JspPath.WELCOME)),
    SIGNUP_GET((req, resp) -> new Router(JspPath.SIGNUP)),
    SIGNUP_POST(new SignupCommand()),
    VERIFY_EMAIL_GET((req, resp) -> new Router(JspPath.VERIFY_EMAIL)),
    VERIFY_EMAIL_POST(new VerifyEmailCommand()),
    LOGIN_GET((req, resp) -> new Router(JspPath.LOGIN)),
    LOGIN_POST(new LoginCommand()),
    RECOVER_PASSWORD_GET((req, resp) -> new Router(JspPath.RECOVER_PASSWORD)),
    RECOVER_PASSWORD_POST(new RecoverPasswordCommand()),
    CHANGE_LANGUAGE_POST(new ChangeLanguageCommand()),
    CHANGE_PASSWORD_GET((req, resp) -> new Router(JspPath.CHANGE_PASSWORD)),
    CHANGE_PASSWORD_POST(new ChangePasswordCommand()),
    LOGOUT(new LogoutCommand()),
    SUCCESS_GET((req, resp) -> new Router(JspPath.SUCCESS_PAGE)),
    PROFILE_GET(new ProfileCommand()),
    CARD_PAGE_GET(new CardPageCommand()),
    MAKE_PAYMENT_POST(new MakePaymentCommand()),
    BLOCK_ACCOUNT_POST(new BlockAccountCommand()),
    TOP_UP_AMOUNT_PAGE_GET(new TopUpAmountPageCommand()),
    TOP_UP_MESSAGE_PAGE_GET(new TopUpMessagePageCommand()),
    WITHDRAW_AMOUNT_PAGE_GET(new WithdrawAmountPageCommand()),
    WITHDRAW_MESSAGE_PAGE_GET(new WithdrawMessagePageCommand()),
    PLACE_BID_POST(new PlaceBidCommand()),
    DELETE_ACCOUNT_POST(new DeleteAccountCommand()),
    CREATE_ACCOUNT_POST(new CreateAccountCommand()),
    DELETE_CARD_POST(new DeleteCardCommand()),
    VERIFY_CREATE_CARD_POST(new VerifyCreateCardCommand()),
    VERIFY_CREATE_CARD_GET((req, resp) -> new Router(JspPath.VERIFY_CREATE_CARD)),
    CREATE_CARD_POST(new CreateCardCommand()),
    ACCOUNT_PAGE_GET(new AccountPageCommand()),
    RUN_ACCOUNTS_GET(new RunAccountsPageCommand()),
    RUN_BIDS_GET(new RunBidsCommand()),
    APPROVE_BID_POST(new ApproveBidCommand()),
    REJECT_BID_POST(new RejectBidCommand()),
    ENABLE_ACCOUNT_POST(new EnableAccountCommand()),
    INCLUDE_ACCOUNTS(new IncludeAccountsCommand()),
    INCLUDE_CARDS(new IncludeCardsCommand()),
    INCLUDE_CURRENCIES(new IncludeCurrenciesCommand());

    private final ActionCommand command;

    CommandType(ActionCommand command) {
        this.command = command;
    }

    public ActionCommand getCurrentCommand() {
        return command;
    }
}
