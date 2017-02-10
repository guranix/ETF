package servlets;

import dbService.DataSets.User;
import service.ETFParser;
import templater.PageGenerator;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.*;


/**
 * Created by guran on 2/7/17.
 */
public class TickersServlet extends HttpServlet {

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


        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("globalTickers", ETFParser.getGlobalEquities());
        pageVariables.put("usTickers", ETFParser.getUSEquities());
        pageVariables.put("username", user.getName());

        response.getWriter().println(PageGenerator.instance().getPage("menu.html", pageVariables));

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);

    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {

        response.sendRedirect("signin");

    }

}