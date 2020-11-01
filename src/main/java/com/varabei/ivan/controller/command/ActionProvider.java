package com.varabei.ivan.controller.command;

import com.varabei.ivan.controller.JspPath;
import com.varabei.ivan.controller.router.Router;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ActionProvider {
    private static final Logger log = LogManager.getLogger(ActionProvider.class);

    private ActionProvider() {
    }

    public static ActionCommand defineAction(String action) {
        ActionCommand command = (req, resp) -> new Router(JspPath.ERROR_404);
        try {
            if (action != null) {
                command = CommandType.valueOf(action.toUpperCase()).getCurrentCommand();
            }
        } catch (IllegalArgumentException e) {
            log.error(e);
        }
        return command;
    }
}
