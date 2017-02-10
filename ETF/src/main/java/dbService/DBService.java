package dbService;

import dbService.DataSets.Etf;
import dbService.DataSets.RequestHistory;
import dbService.DataSets.User;
import dbService.dao.EtfDAO;
import dbService.dao.RequestHisroryDAO;
import dbService.dao.UserDAO;
import org.h2.jdbcx.JdbcDataSource;
import java.sql.*;
import java.util.List;
import java.util.Map;

/**
 * Created by guran on 2/7/17.
 */
public class DBService {
    private final Connection connection;

    public DBService() {this.connection = getH2Connection();}
//    public DBService() { this.connection = getMysqlConnection();}

    public Etf getEtf(String ticker) throws DBException {
        try {
            return (new EtfDAO(connection).get(ticker));
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    public long getEtfID(Etf etf) throws DBException {
        try {
            return (new EtfDAO(connection).getID(etf));
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    public void addEtf(Etf etf) throws DBException {
        try {
            connection.setAutoCommit(false);
            EtfDAO dao = new EtfDAO(connection);
            dao.createTable();
            dao.insert(etf);
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ignore) {
            }
            throw new DBException(e);
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ignore) {
            }
        }
    }

    public User getUser(String username) throws DBException {
        try {
            return (new UserDAO(connection).get(username));
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    public void addUser(User user) throws DBException {
        try {
            connection.setAutoCommit(false);
            UserDAO dao = new UserDAO(connection);
            dao.createTable();
            dao.insert(user);
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ignore) {
            }
            throw new DBException(e);
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ignore) {
            }
        }
    }


    public List<RequestHistory> getRequertHistory(User user) throws DBException {
        try {
            return (new RequestHisroryDAO(connection).get(user));
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    public Map<Timestamp, String> getRequertHistoryMap(User user) throws DBException {
        try {
            return (new RequestHisroryDAO(connection).getMap(user));
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    public void addToRequestHistory(User user, Etf etf) throws DBException {
        try {
            connection.setAutoCommit(false);
            RequestHisroryDAO dao = new RequestHisroryDAO(connection);
            dao.createTable();
            dao.insert(user, etf);
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ignore) {
            }
            throw new DBException(e);
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ignore) {
            }
        }
    }


    public void printConnectInfo() {
        try {
            System.out.println("DB name: " + connection.getMetaData().getDatabaseProductName());
            System.out.println("DB version: " + connection.getMetaData().getDatabaseProductVersion());
            System.out.println("Driver: " + connection.getMetaData().getDriverName());
            System.out.println("Autocommit: " + connection.getAutoCommit());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("UnusedDeclaration")
    public static Connection getMysqlConnection() {
        try {
            DriverManager.registerDriver((Driver) Class.forName("com.mysql.cj.jdbc.Driver").newInstance());

            StringBuilder url = new StringBuilder();

            url.
                    append("jdbc:mysql://").        //db type
                    append("localhost:").           //host name
                    append("3306/").                //port
                    append("etf?").                 //db name
                    append("user=root&").           //login
                    append("password=root&").       //password
                    append("verifyServerCertificate=false&useSSL=true");

//            System.out.println("URL: " + url + "\n");

            Connection connection = DriverManager.getConnection(url.toString());
            return connection;
        } catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Connection getH2Connection() {
        try {
            String url = "jdbc:h2:./h2db";
            String name = "root";
            String pass = "root";

            JdbcDataSource ds = new JdbcDataSource();
            ds.setURL(url);
            ds.setUser(name);
            ds.setPassword(pass);

            Connection connection = DriverManager.getConnection(url, name, pass);
            return connection;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}