package com.varabei.ivan.controller.command.client;

import com.varabei.ivan.controller.command.ActionCommand;
import com.varabei.ivan.controller.command.impl.*;


public enum CommandType {
    SIGNUP_GET(new SignUpGetCommand()),
    SIGNUP_POST(new SignUpPostCommand()),
    LOGIN_GET(new LoginGetCommand()),
    LOGIN_POST(new LoginPostCommand()),
    LOGOUT(new LogoutCommand()),
    PROFILE(new ProfileCommand()),
    CARD_PAGE_GET(new CardPageGetCommand()),
    MAKE_PAYMENT_POST(new MakePaymentPost()),
    PAYMENT_OVERVIEW_GET(new PaymentOverviewGet()),
    BLOCK_ACCOUNT_POST(new BlockAccountCommand()),
    MODERATE_ACCOUNTS_GET(new ModerateAccountsGet()),
    UNBLOCK_ACCOUNT_POST(new UnblockAccountPost()),
    SET_TOP_UP_BID_POST(new SetTopUpBidCommand()),
    MODERATE_TOP_UP_BIDS_GET(new ModerateTopUpBidsGet()),
    APPROVE_TOP_UP_BID(new ApproveTopUpBid()),
    REJECT_TOP_UP_BID(new RejectTopUpBid()),
    INCLUDE_ACCOUNTS(new IncludeAccountsGet());
    private final ActionCommand command;

    CommandType(ActionCommand command) {
        this.command = command;
    }

    public ActionCommand getCurrentCommand() {
        return command;
    }
}
