package com.by.epam;


import javax.servlet.http.HttpServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Properties;

public class Serv extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter pw = resp.getWriter();
        pw.println("new Const().s");
        Properties properties = new Properties();

        try (InputStream is = getClass().getClassLoader()
                .getResourceAsStream("app.properties")) {

            properties.load(is);
            String url = properties.getProperty("p1");
            pw.println(url);
        }
    }
}
