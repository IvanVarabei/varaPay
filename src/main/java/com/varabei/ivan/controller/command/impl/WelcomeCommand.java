package com.varabei.ivan.controller.command.impl;

import com.varabei.ivan.controller.command.ActionCommand;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import com.varabei.ivan.controller.command.param.ResponseParam;
import com.varabei.ivan.model.service.ServiceException;
import com.varabei.ivan.model.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EmptyCommand implements ActionCommand {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        try {
            //new DefaultPaymentService().makePayment(2l,1l,new BigDecimal(1));
//            User user = new User();
//            user.setLogin("vaasera");
//            user.setPassword("sadfasdf");
//            user.setFirstName("f");
//            user.setLastName("l");
//            user.setEmail("valsdf23");
//            user.setBirth(LocalDate.now());
//            new UserServiceImpl().registration(user);
            new UserServiceImpl().signIn("ivan","qqq3");
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        req.getRequestDispatcher("/WEB-INF/pages/main.jsp").forward(req, resp);
    }
}
