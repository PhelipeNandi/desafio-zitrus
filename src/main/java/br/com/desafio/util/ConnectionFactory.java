package br.com.desafio.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    public ConnectionFactory() {
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:mysql://localhost:13306/desafio", "root", "root");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
