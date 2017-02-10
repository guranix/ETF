package dbService.dao;

import dbService.DataSets.Etf;
import dbService.DataSets.RequestHistory;
import dbService.DataSets.User;
import dbService.executor.Executor;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;

/**
 * Created by guran on 2/7/17.
 */
public class RequestHisroryDAO {

    private Executor executor;

    public RequestHisroryDAO(Connection connection) {
        this.executor = new Executor(connection);
    }

    public List<RequestHistory> get(User user) throws SQLException {

        List<RequestHistory> result = new ArrayList<>();

        return executor.execQuery("select * from request_history where user_id='"+ user.getId() +"'", resultSet -> {
            while (resultSet.next()) {
                result.add(new RequestHistory (resultSet.getLong(1), resultSet.getLong(2), resultSet.getDate(3)));
            }
            return result;
        });
    }

    public Map<Timestamp, String> getMap(User user) throws SQLException {

        Map<Timestamp, String> result = new HashMap<>();

        return executor.execQuery("select datetime, etf_name from request_history INNER JOIN etfs\n" +
                " ON request_history.etf_id=etfs.id where request_history.user_id='"+ user.getId() +"'",
            resultSet -> {
            while (resultSet.next()) {
                result.put(resultSet.getTimestamp(1), resultSet.getString(2));
            }
            return result;
        });
    }

    public void insert(User user, Etf etf) throws SQLException {


        executor.execUpdate("insert into request_history (user_id, etf_id, datetime) values " +
                "('" + user.getId() + "', '" + etf.getId() + "', '" + new java.sql.Timestamp(System.currentTimeMillis()) + "')");
    }

    public void createTable() throws SQLException {
        executor.execUpdate("CREATE TABLE IF NOT EXISTS request_history\n" +
                "(\n" +
                "  id          BIGINT   NOT NULL AUTO_INCREMENT,\n" +
                "  user_id     BIGINT   NOT NULL,\n" +
                "  etf_id      BIGINT   NOT NULL,\n" +
                "  datetime    DATETIME NOT NULL,\n" +
                "  primary key (id)" +
                ")");
    }

    public void dropTable() throws SQLException {
        executor.execUpdate("drop table request_history");
    }

}
