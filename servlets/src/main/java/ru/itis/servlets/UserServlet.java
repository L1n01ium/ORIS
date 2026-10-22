package ru.itis.servlets;

import ru.itis.repository.UsersRepository;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/users")
public class UserServlet extends HttpServlet {
//    private List<User> users;
    private UsersRepository usersRepository;

    @Override
    public void init(ServletConfig config) throws ServletException {
        usersRepository = (UsersRepository) config.getServletContext().getAttribute("usersRepository");
//        users = new ArrayList<>();
//        User userOne = User.builder()
//                .id(1L)
//                .name("Danil")
//                .surname("Smirnov")
//                .age(15)
//                .build();
//        User userTwo = User.builder()
//                .id(1L)
//                .name("Said")
//                .surname("Garifov")
//                .age(19)
//                .build();
//        User userThree = User.builder()
//                .id(1L)
//                .name("Nikolai")
//                .surname("Kozlov")
//                .age(18)
//                .build();
//
//        users.add(userOne);
//        users.add(userTwo);
//        users.add(userThree);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        PrintWriter writer = resp.getWriter();
//
//        StringBuilder resultHtml = new StringBuilder();
//
//        resultHtml.append("<!DOCTYPE html>\n" +
//                "<html lang=\"en\">\n" +
//                "<head>\n" +
//                "    <meta charset=\"UTF-8\">\n" +
//                "    <title>Users</title>\n" +
//                "</head>\n" +
//                "<body>\n" +
//                "<h1>Users</h1>\n" +
//                "<div>\n" +
//                "    <table>\n" +
//                "        <tr>\n" +
//                "            <th>ID</th>\n" +
//                "            <th>FIRST NAME</th>\n" +
//                "            <th>LAST NAME</th>\n" +
//                "            <th>AGE</th>\n" +
//                "        </tr>\n");
//
//        for (User user : users) {
//            resultHtml.append("<tr>\n");
//            resultHtml.append("<td>").append(user.getId()).append("</td>\n");
//            resultHtml.append("<td>").append(user.getName()).append("</td>\n");
//            resultHtml.append("<td>").append(user.getSurname()).append("</td>\n");
//            resultHtml.append("<td>").append(user.getAge()).append("</td>\n");
//            resultHtml.append("</tr>");
//        }
//
//        resultHtml.append("    </table>\n" +
//                "</div>\n" +
//               "</body>\n" +
//                "</html>");
//
//        writer.write(resultHtml.toString());
        List list = usersRepository.findAll();
        System.out.println(list.size());
        req.setAttribute("usersForJsp", usersRepository.findAll());
        HttpSession httpSession = req.getSession();
        System.out.println(httpSession.getAttribute("authenticated"));
        req.getRequestDispatcher("/jsp/users.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }


}