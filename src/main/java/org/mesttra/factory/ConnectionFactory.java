package org.mesttra.factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    private final String url = "jdbc:postgresql://127.0.0.1:5432/sistema_bancario";
    private final String user = "postgres";
    private final String password = "123";

    public Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            System.err.println("Não foi possivel conectar ao banco de dados.");
            System.err.println(e.getMessage());
        }
        return connection;
    }
}