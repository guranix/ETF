package servlets;

import dbService.DBException;
import dbService.DBService;
import dbService.DataSets.Etf;
import dbService.DataSets.User;
import service.ETFParser;
import templater.PageGenerator;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by guran on 2/7/17.
 */
public class EtfServlet extends HttpServlet {

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


        String ticker = request.getParameter("ticker");

        if (ticker == null){
            response.sendRedirect("signin");
        }

        DBService dbService = new DBService();
        Etf etf = null;

        try {
            etf = dbService.getEtf(ticker);
        } catch (DBException e) {
            e.printStackTrace();
            etf = new ETFParser().getEtf(ticker);
            try {
                dbService.addEtf(etf);
                etf.setId(dbService.getEtfID(etf));
            } catch (DBException e1) {
                e1.printStackTrace();
            }
        } finally {
            try {
                dbService.addToRequestHistory(user, etf);
            } catch (DBException e) {
                e.printStackTrace();
            }
        }


        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("username", user.getName());
        pageVariables.put("etfName", etf.getName());
        pageVariables.put("etfDescription", etf.getDescription());
        pageVariables.put("top10Holdings", etf.getTop10Holdings());
        pageVariables.put("countryWeights", etf.getCountryWeight());
        pageVariables.put("sectorWeights", etf.getSectorWeight());

        response.getWriter().println(PageGenerator.instance().getPage("etf.html", pageVariables));

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);

    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {

        response.sendRedirect("signin");

    }

}