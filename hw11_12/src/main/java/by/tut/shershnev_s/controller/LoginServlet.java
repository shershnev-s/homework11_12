package by.tut.shershnev_s.controller;

import by.tut.shershnev_s.service.RoleService;
import by.tut.shershnev_s.service.UserService;
import by.tut.shershnev_s.service.impl.RoleServiceImpl;
import by.tut.shershnev_s.service.impl.UserServiceImpl;
import by.tut.shershnev_s.service.model.LoginDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.invoke.MethodHandles;


public class LoginServlet extends HttpServlet {

    private static final String PAGES = "/WEB-INF/pages/";
    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private UserService userService = UserServiceImpl.getInstance();
    private RoleService roleService = RoleServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String forward = PAGES + "login.jsp";
        RequestDispatcher view = getServletContext().getRequestDispatcher(forward);
        view.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }


}
