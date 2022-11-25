package org.mesttra.factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    private static final String url = "jdbc:postgresql://127.0.0.1:5432/sistema_bancario";
    private static final String user = "postgres";
    private static final String password = "123";

    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Conectado no banco de dados.");
        } catch (SQLException e) {
            System.err.println("Não foi possivel conectar ao banco de dados.");
            System.err.println(e.getMessage());
        }
        return connection;
    }
}