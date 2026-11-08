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
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/selects")
public class SelectServlet extends HttpServlet {

    private UsersService usersService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        usersService = (UsersService) config.getServletContext().getAttribute("usersService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/plain;charset=UTF-8");
        PrintWriter out = resp.getWriter();

        try {
            print(out, "where city = 'Казань'", usersService.findByCity("Казань"));
            print(out, "where age > 18", usersService.findByAgeGreaterThan(18));
            print(out, "where phone = '+79000000000'", usersService.findByPhone("+79000000000"));
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    private void print(PrintWriter out, String title, List<User> users) {
        System.out.println("=== " + title + " ===");
        out.println("=== " + title + " ===");
        System.out.println("найдено: " + users.size());
        out.println("найдено: " + users.size());

        for (User user : users) {
            String line = user.getId() + " | " + user.getName() + " " + user.getSurname()
                    + " | city=" + user.getCity()
                    + " | age=" + user.getAge()
                    + " | phone=" + user.getPhone();
            System.out.println(line);
            out.println(line);
        }
        System.out.println();
        out.println();
    }
}
