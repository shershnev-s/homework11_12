package by.tut.shershnev_s.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AdminServlet extends HttpServlet {
    private static final String PAGES = "/WEB-INF/pages/";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String forward = PAGES + "admin.jsp";
        RequestDispatcher view = getServletContext().getRequestDispatcher(forward);
        view.forward(req, resp);
    }
}
