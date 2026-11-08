package ru.itis.servlets;

import ru.itis.models.User;
import ru.itis.services.UsersService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/save")
public class SaveServlet extends HttpServlet {

    private UsersService usersService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        usersService = (UsersService) config.getServletContext().getAttribute("usersService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int count = usersService.getUsersCount();
            req.setAttribute("count", count);
            req.getRequestDispatcher("/jsp/save.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int count = Integer.parseInt(req.getParameter("count"));
        List<User> users = new ArrayList<>();

        for (int i = 1; i <= count; i++) {
            String email = req.getParameter("email_" + i);
            if (email == null || email.trim().isEmpty()) {
                continue;
            }

            User user = User.builder()
                    .name(req.getParameter("firstName_" + i))
                    .surname(req.getParameter("lastName_" + i))
                    .email(email.trim())
                    .password("qwerty007")
                    .age(parseAge(req.getParameter("age_" + i)))
                    .city(req.getParameter("city_" + i))
                    .phone(req.getParameter("phone_" + i))
                    .build();
            users.add(user);
        }

        try {
            usersService.addUsers(users);
        } catch (SQLException e) {
            throw new ServletException(e);
        }

        resp.sendRedirect(req.getContextPath() + "/html/saved.html");
    }

    private Integer parseAge(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        return Integer.parseInt(value.trim());
    }
}
