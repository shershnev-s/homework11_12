package by.tut.shershnev_s.controller.filter;


import by.tut.shershnev_s.service.LoginService;
import by.tut.shershnev_s.service.impl.LoginServiceImpl;
import by.tut.shershnev_s.service.model.LoginDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AuthenticationFilter implements Filter {

    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private LoginService loginService = LoginServiceImpl.getInstance();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        List<String> validateResult = validateData(httpServletRequest);
        if (!validateResult.isEmpty()) {
            httpServletRequest.setAttribute("errors", validateResult);
        } else {
            LoginDTO loginDTO = setLoginDTO(httpServletRequest);
            Long id = loginService.validate(loginDTO);
            String role = getRole(id);
            HttpSession session = httpServletRequest.getSession();
            session.setAttribute("login", loginDTO.getLogin());
            session.setAttribute("role", role);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private LoginDTO setLoginDTO(HttpServletRequest httpServletRequest) {
        String name = httpServletRequest.getParameter("name");
        String password = httpServletRequest.getParameter("password");
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setLogin(name);
        loginDTO.setPassword(password);
        return loginDTO;
    }

    private List<String> validateData(HttpServletRequest req) {
        String name = req.getParameter("name");
        String password = req.getParameter("password");
        List<String> result = new ArrayList<>();
        result.addAll(validateName(name));
        result.addAll(validatePassword(password));
        return result;
    }

    private List<String> validateName(String name) {
        List<String> result = new ArrayList<>();
        int maxLength = 15;
        if (name.length() > maxLength) {
            logger.error("User entered wrong format data in " + name);
            result.add("Use" + maxLength + "chars!");
        }
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9_.-]*$");
        Matcher matcher = pattern.matcher(name);
        if (!matcher.matches()) {
            logger.error("User entered wrong format data in " + name);
            result.add("Use only eng letters in " + name);
        }
        if (name.isEmpty()) {
            logger.error("User entered wrong format data in " + name);
            result.add("Empty field in " + name);
        }
        return result;
    }

    private List<String> validatePassword(String password) {
        List<String> result = new ArrayList<>();
        int maxLength = 9;
        if (password.length() > maxLength) {
            logger.error("User entered wrong format data in " + password);
            result.add("Use " + maxLength + " chars!");
        }
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9]+$");
        Matcher matcher = pattern.matcher(password);
        if (!matcher.matches()) {
            logger.error("User entered wrong format data in " + password);
            result.add("Use only eng letters in " + password);
        }
        if (password.isEmpty()) {
            logger.error("User entered wrong format data in " + password);
            result.add("Empty field in " + password);
        }
        return result;
    }

    private String getRole(Long id) {
        Long idForAdmin = 1L;
        Long idForUser = 2L;
        HashMap<Long, String> map = new HashMap();
        map.put(idForAdmin, "admin");
        map.put(idForUser, "user");
        return map.get(id);
    }
}
