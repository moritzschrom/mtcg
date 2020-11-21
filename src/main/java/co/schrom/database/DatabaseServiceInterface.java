package co.schrom.database;

import java.sql.Connection;

public interface DatabaseServiceInterface {
    Connection getConnection();
}
