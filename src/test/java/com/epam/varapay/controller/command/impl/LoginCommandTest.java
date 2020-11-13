package com.epam.varapay.controller.command.impl;

import com.epam.varapay.controller.router.Router;
import com.epam.varapay.model.entity.Role;
import com.epam.varapay.model.entity.User;
import com.epam.varapay.exception.ServiceException;
import com.epam.varapay.model.service.UserService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.testng.Assert.assertEquals;

public class LoginCommandTest {
    @Mock
    private UserService userService;
    @Mock
    private HttpServletRequest req;
    @Mock
    private HttpSession session;
    @Mock
    private User user;
    @InjectMocks
    private LoginCommand loginCommand = new LoginCommand();

    @BeforeMethod
    private void setUp() throws ServiceException {
        initMocks(this);
        when(req.getParameter("password")).thenReturn("qwerty");
        when(req.getParameter("login")).thenReturn("user1");
        when(req.getSession()).thenReturn(session);
        when(user.getRole()).thenReturn(Role.ADMIN);
        when(userService.signIn("user1", "qwerty")).thenReturn(Optional.of(user));
    }

    @Test
    public void executeUserPresent() {
        loginCommand.execute(req, null);

        verify(session).setAttribute("login", "user1");
    }

    @Test
    public void executeUserNotFound() throws ServiceException {
        when(userService.signIn("user1", "qwerty")).thenReturn(Optional.empty());

        loginCommand.execute(req, null);

        verify(req).setAttribute("error", true);
    }

    @Test
    public void executeUserExceptionThrown() throws ServiceException {
        doThrow(ServiceException.class).when(userService).signIn("user1", "qwerty");

        Router actual = loginCommand.execute(req, null);
        Router expected = new Router("/WEB-INF/pages/error/error500.jsp");

        assertEquals(actual, expected);
    }
}
