package dbService.executor;

import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by guran on 2/7/17.
 */
public interface ResultHandler<T> {
    T handle(ResultSet resultSet) throws SQLException;
}