package br.com.board.persistence.config;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import io.github.cdimascio.dotenv.Dotenv;

@NoArgsConstructor(access = PRIVATE)
public final class ConnectionConfig {

    private static final Dotenv dotenv = Dotenv.load();

    public static Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/board_db";
        String user = dotenv.get("MYSQL_USER");
        String password = dotenv.get("MYSQL_PASSWORD");
        Connection connection = DriverManager.getConnection(url, user, password);
        connection.setAutoCommit(false);

        return connection;
    }
}
