package main;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import service.ETFParser;
import servlets.*;

/**
 * Created by guran on 2/7/17.
 */
public class Main {
    public static void main(String[] args) throws Exception {

        SignInServlet signInServlet = new SignInServlet();
        SignUpServlet signUpServlet = new SignUpServlet();
        TickersServlet tickersServlet = new TickersServlet();
        EtfServlet etfServlet = new EtfServlet();
        UserServlet userServlet = new UserServlet();

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(signInServlet), "/");
        context.addServlet(new ServletHolder(signUpServlet), "/signup");
        context.addServlet(new ServletHolder(tickersServlet), "/tickers");
        context.addServlet(new ServletHolder(etfServlet), "/etf");
        context.addServlet(new ServletHolder(userServlet), "/user");

        Server server = new Server(8080);
        server.setHandler(context);

        server.start();
        server.join();
    }
}