package servlets;

import dbService.DBException;
import dbService.DBService;
import dbService.DataSets.User;
import service.ETFParser;
import templater.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * Created by guran on 2/7/17.
 */
public class UserServlet extends HttpServlet {

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {

        User user = null;

        HttpSession session = request.getSession(true);
        if (session.getAttribute("user") instanceof User) {
            user = (User) session.getAttribute("user");
        }
        else {
            response.sendRedirect("signin");
            return;
        }

        session.setAttribute("user", user);

        Map<Timestamp, String> history = new HashMap<>();

        DBService dbService = new DBService();
        try {

            history = dbService.getRequertHistoryMap(user);

        } catch (DBException e) {
            e.printStackTrace();
        }


        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("username", user.getUsername());
        pageVariables.put("name", user.getName());
        pageVariables.put("email", user.getEmail());
        pageVariables.put("history", history);

        response.getWriter().println(PageGenerator.instance().getPage("user.html", pageVariables));

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);

    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {

        response.sendRedirect("signin");

    }

}