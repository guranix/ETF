package servlets;

import dbService.DBException;
import dbService.DBService;
import dbService.DataSets.User;
import templater.PageGenerator;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by guran on 2/7/17.
 */
public class SignInServlet extends HttpServlet {

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {

        response.getWriter().println(PageGenerator.instance().getPage("signin.html", null));

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);

    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {

        try {
            User user = new DBService().getUser(request.getParameter("username"));
            if (user.chackPassword(request.getParameter("password"))) {
                HttpSession session = request.getSession(true);
                session.setAttribute("user", user);
                response.sendRedirect("tickers");
            } else {
                response.sendRedirect("signin");
            }

        } catch (DBException e) {
            e.printStackTrace();
            response.sendRedirect("signin");
        }

    }


}