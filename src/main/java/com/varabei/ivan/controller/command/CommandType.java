package com.varabei.ivan.controller.command;

import com.varabei.ivan.controller.command.impl.*;

public enum CommandType {
    SIGNUP_GET(new SignupPageCommand()),
    SIGNUP_POST(new SignupCommand()),
    VERIFY_EMAIL_POST(new VerifyEmailCommand()),
    LOGIN_GET(new LoginPageCommand()),
    LOGIN_POST(new LoginCommand()),
    LOGOUT(new LogoutCommand()),
    SUCCESS_GET(new SuccessPageCommand()),
    PROFILE_GET(new ProfileCommand()),
    CARD_PAGE_GET(new CardPageCommand()),
    MAKE_PAYMENT_POST(new MakePaymentCommand()),
    BLOCK_ACCOUNT_POST(new BlockAccountCommand()),
    PLACE_TOP_UP_BID_POST(new PlaceTopUpBidCommand()),
    PLACE_WITHDRAW_BID_POST(new PlaceWithdrawBidCommand()),
    DELETE_ACCOUNT_POST(new DeleteAccountCommand()),
    CREATE_ACCOUNT_POST(new CreateAccountCommand()),
    DELETE_CARD_POST(new DeleteCardCommand()),
    VERIFY_EMAIL_GET(new VerifyEmailPageCommand()),
    VERIFY_CREATE_CARD_POST(new VerifyCreateCardCommand()),
    VERIFY_CREATE_CARD_GET(new VerifyCreateCardPageCommand()),
    CREATE_CARD_POST(new CreateCardCommand()),
    ACCOUNT_PAGE_GET(new AccountPageCommand()),
    TOP_UP_AMOUNT_PAGE_GET(new TopUpAmountPageCommand()),
    TOP_UP_PAGE_GET(new TopUpPageCommand()),
    RECOVER_PASSWORD_GET(new RecoverPasswordPageCommand()),
    RECOVER_PASSWORD_POST(new RecoverPasswordCommand()),
    CHANGE_PASSWORD_GET(new ChangePasswordPageCommand()),
    CHANGE_PASSWORD_POST(new ChangePasswordCommand()),
    CHANGE_LANGUAGE(new ChangeLanguageCommand()),

    RUN_ACCOUNTS_GET(new RunAccountsPageCommand()),
    RUN_BIDS_GET(new RunBidsCommand()),
    APPROVE_TOP_UP_BID_POST(new ApproveTopUpBidCommand()),
    APPROVE_WITHDRAW_BID_POST(new ApproveWithdrawBidCommand()),
    REJECT_TOP_UP_BID_POST(new RejectTopUpBidCommand()),
    REJECT_WITHDRAW_BID_POST(new RejectWithdrawBidCommand()),
    UNBLOCK_ACCOUNT_POST(new UnblockAccountCommand()),

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
