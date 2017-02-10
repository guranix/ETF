package dbService.dao;

import dbService.DataSets.Etf;
import dbService.executor.Executor;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by guran on 2/7/17.
 */
public class EtfDAO {

    private Executor executor;

    public EtfDAO(Connection connection) {
        this.executor = new Executor(connection);
    }

    public Etf get(String ticker) throws SQLException {
        return executor.execQuery("select * from etfs where ticker='" + ticker +"'", result -> {
            result.next();
            return new Etf(result.getLong(1), result.getString(2), result.getString(3), result.getString(4), result.getString(5),
                    result.getString(6), result.getString(7));
        });
    }

    public long getID(Etf etf) throws SQLException {
        return executor.execQuery("select id from etfs where ticker='" + etf.getTicker() +"'", result -> {
            result.next();
            return result.getLong(1);
        });
    }

    public void insert(Etf etf) throws SQLException {

        executor.execUpdate("insert into etfs (etf_name, ticker, description, top_holdings, country_weight, sector_weight) values " +
                "('" + etf.getName() + "', '" + etf.getTicker() + "', '" + etf.getDescription() + "', '" + etf.getTop10Holdings() + "', '"
                + etf.getCountryWeight() + "', '" + etf.getSectorWeight() + "')");
    }

    public void createTable() throws SQLException {
        executor.execUpdate("CREATE TABLE IF NOT EXISTS etfs\n" +
                "(\n" +
                    " id   BIGINT    NOT NULL AUTO_INCREMENT," +
                    " etf_name       VARCHAR(100)  NOT NULL," +
                    " ticker         VARCHAR(10)   NOT NULL UNIQUE," +
                    " description    VARCHAR(1024) NOT NULL," +
                    " top_holdings   VARCHAR(1024) NOT NULL," +
                    " country_weight VARCHAR(1024) NOT NULL," +
                    " sector_weight  VARCHAR(1024) NOT NULL," +
                    " primary key (id)" +
                ")");
    }

    public void dropTable() throws SQLException {
        executor.execUpdate("drop table etfs");
    }

}