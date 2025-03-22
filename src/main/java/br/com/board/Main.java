package br.com.board;

import java.sql.Connection;
import java.sql.SQLException;

import br.com.board.persistence.migration.MigrationStrategy;
import br.com.board.ui.MainMenu;

import static br.com.board.persistence.config.ConnectionConfig.getConnection;

public class Main {
    public static void main(String[] args) throws SQLException {
        try (Connection connection = getConnection()) {
            new MigrationStrategy(connection).executeMigration();
        }
        new MainMenu().execute();
    }
}
