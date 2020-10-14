package com.varabei.ivan.controller.command.client;

import com.varabei.ivan.controller.command.ActionCommand;
import com.varabei.ivan.controller.command.impl.*;


public enum CommandType {
    SIGNUP_GET(new SignupPageCommand()),
    SIGNUP_POST(new SignupCommand()),
    LOGIN_GET(new LoginPageCommand()),
    LOGIN_POST(new LoginCommand()),
    LOGOUT(new LogoutCommand()),
    PROFILE_GET(new ProfileCommand()),
    CARD_PAGE_GET(new CardPageCommand()),
    MAKE_PAYMENT_POST(new MakePaymentCommand()),
    PAYMENT_OVERVIEW_GET(new PaymentOverviewCommand()),
    BLOCK_ACCOUNT_POST(new BlockAccountCommand()),
    PLACE_TOP_UP_BID_POST(new PlaceTopUpBidCommand()),
    PLACE_WITHDRAW_BID_POST(new PlaceWithdrawBidCommand()),

    RUN_ACCOUNTS_GET(new RunAccountsPageCommand()),
    RUN_BIDS_GET(new RunBidsCommand()),
    APPROVE_TOP_UP_BID_POST(new ApproveTopUpBidCommand()),
    APPROVE_WITHDRAW_BID_POST(new ApproveWithdrawBidCommand()),
    REJECT_TOP_UP_BID_POST(new RejectTopUpBidCommand()),
    REJECT_WITHDRAW_BID_POST(new RejectWithdrawBidCommand()),
    UNBLOCK_ACCOUNT_POST(new UnblockAccountCommand()),

    INCLUDE_ACCOUNTS(new IncludeAccountsGet()),
    INCLUDE_CARDS(new IncludeCards());
    private final ActionCommand command;

    CommandType(ActionCommand command) {
        this.command = command;
    }

    public ActionCommand getCurrentCommand() {
        return command;
    }
}
