package com.varabei.ivan.model.service;


import com.varabei.ivan.model.service.impl.DefaultUserService;

public final class ServiceFactory {
    private static final ServiceFactory instance = new ServiceFactory();
    private final UserService userService = new DefaultUserService();

    private ServiceFactory() {
    }

    public UserService getUserService() {
        return userService;
    }

    public static ServiceFactory getInstance() {
        return instance;
    }
}