package co.schrom.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseService implements DatabaseServiceInterface {
    private static DatabaseService instance;

    private static final String DB_URL = "jdbc:mysql://localhost/monster_trading_card_game";
    private static final String USER = "admin";
    private static final String PASS = "admin";

    private DatabaseService() {
    }

    public static DatabaseService getInstance() {
        if (DatabaseService.instance == null) {
            DatabaseService.instance = new DatabaseService();
        }
        return DatabaseService.instance;
    }

    @Override
    public Connection getConnection() {
        try {
            return DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
