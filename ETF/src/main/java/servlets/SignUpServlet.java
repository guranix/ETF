package servlets;

import dbService.DBException;
import dbService.DBService;
import dbService.DataSets.User;
import templater.PageGenerator;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * Created by guran on 2/7/17.
 */
public class SignUpServlet extends HttpServlet {

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {

        response.getWriter().println(PageGenerator.instance().getPage("signup.html", null));

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);

    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {

        User user = new User(request.getParameter("username"), request.getParameter("password"),
                request.getParameter("name"),request.getParameter("email"));

        try {
            DBService dbService = new DBService();
            dbService.addUser(user);
            response.sendRedirect("signin");

        } catch (DBException e) {
            e.printStackTrace();
        }

    }
}