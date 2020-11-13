package com.epam.varapay.controller.command;

import com.epam.varapay.model.entity.Role;

import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class CommandRight {
    public static final Map<CommandType, List<Role>> commandRoles = new EnumMap<>(CommandType.class);

    static {
        List<Role> adminClient = Collections.unmodifiableList(List.of(Role.ADMIN, Role.CLIENT));
        List<Role> admin = Collections.unmodifiableList(List.of(Role.ADMIN));
        List<Role> client = List.of(Role.CLIENT);
        commandRoles.put(CommandType.PROFILE_GET, adminClient);
        commandRoles.put(CommandType.RECOVER_PASSWORD_GET, adminClient);
        commandRoles.put(CommandType.RECOVER_PASSWORD_POST, adminClient);
        commandRoles.put(CommandType.CHANGE_PASSWORD_GET, adminClient);
        commandRoles.put(CommandType.CHANGE_PASSWORD_POST, adminClient);
        commandRoles.put(CommandType.SUCCESS_GET, adminClient);

        commandRoles.put(CommandType.CARD_PAGE_GET, client);
        commandRoles.put(CommandType.MAKE_PAYMENT_POST, client);
        commandRoles.put(CommandType.BLOCK_ACCOUNT_POST, client);
        commandRoles.put(CommandType.PLACE_BID_POST, client);
        commandRoles.put(CommandType.CREATE_ACCOUNT_POST, client);
        commandRoles.put(CommandType.DELETE_ACCOUNT_POST, client);
        commandRoles.put(CommandType.CREATE_CARD_POST, client);
        commandRoles.put(CommandType.DELETE_CARD_POST, client);
        commandRoles.put(CommandType.VERIFY_CREATE_CARD_GET, client);
        commandRoles.put(CommandType.VERIFY_CREATE_CARD_POST, client);
        commandRoles.put(CommandType.ACCOUNT_PAGE_GET, client);
        commandRoles.put(CommandType.TOP_UP_AMOUNT_PAGE_GET, client);
        commandRoles.put(CommandType.TOP_UP_MESSAGE_PAGE_GET, client);

        commandRoles.put(CommandType.RUN_ACCOUNTS_GET, admin);
        commandRoles.put(CommandType.RUN_BIDS_GET, admin);
        commandRoles.put(CommandType.APPROVE_BID_POST, admin);
        commandRoles.put(CommandType.REJECT_BID_POST, admin);
        commandRoles.put(CommandType.ENABLE_ACCOUNT_POST, admin);
    }

    private CommandRight() {
    }
}
