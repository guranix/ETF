package dbService.dao;

import dbService.DataSets.User;
import dbService.executor.Executor;
import service.MD5;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by guran on 2/7/17.
 */
public class UserDAO {

    private Executor executor;

    public UserDAO(Connection connection) {
        this.executor = new Executor(connection);
    }

    public User get(String username) throws SQLException {
        return executor.execQuery("select * from users where username='" + username + "'" , result -> {
            result.next();
            return new User(result.getLong(1), result.getString(2), result.getString(3), result.getString(4), result.getString(5));
        });
    }

    public void insert(User user) throws SQLException {
        executor.execUpdate("insert into users (username, password, name, email) values " +
                "('" + user.getUsername() + "', '" + MD5.encode(user.getPassword()) + "', '"
                + user.getName() + "', '" + user.getEmail() + "')");
    }

    public void createTable() throws SQLException {
        executor.execUpdate("CREATE TABLE IF NOT EXISTS users\n" +
                "(\n" +
                "  id          BIGINT NOT NULL AUTO_INCREMENT,\n" +
                "  username    VARCHAR(35) NOT NULL UNIQUE ,\n" +
                "  password    VARCHAR(35) NOT NULL,\n" +
                "  name        VARCHAR(35) NOT NULL,\n" +
                "  email       VARCHAR(35) NOT NULL UNIQUE,\n" +
                "  primary key (id)" +
                ")");
    }

    public void dropTable() throws SQLException {
        executor.execUpdate("drop table users");
    }

}
