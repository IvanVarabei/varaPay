package com.epam.varapay.controller.filter;

import org.mockito.Mock;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Locale;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class LocaleFilterTest {
    @Mock
    private HttpServletRequest req;
    @Mock
    private HttpSession session;
    @Mock
    private FilterChain filterChain;
    private Locale locale = new Locale("en");
    private LocaleFilter localeFilter = new LocaleFilter();

    @BeforeMethod
    public void setup() {
        initMocks(this);
        when(req.getSession()).thenReturn(session);
        when(req.getLocale()).thenReturn(locale);
    }

    @Test
    public void doFilterSetNewLocale() throws IOException, ServletException {
        localeFilter.doFilter(req, null, filterChain);

        verify(session).setAttribute("locale", locale);
    }

    @Test
    public void doFilterLocaleAlreadyPresent() throws IOException, ServletException {
        localeFilter.doFilter(req, null, filterChain);

        verify(filterChain).doFilter(req, null);
    }
}
