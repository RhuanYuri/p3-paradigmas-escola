package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

    // Caminho do arquivo do banco (pode ser relativo ou absoluto)
    private static final String URL = "jdbc:sqlite:database.db";

    public static Connection getConnection() {
        try {
            Class.forName("org.sqlite.JDBC");
            return DriverManager.getConnection(URL);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("ERRO: O arquivo JAR do SQLite não foi adicionado corretamente ao projeto!", e);
        } catch (SQLException e) {
            throw new RuntimeException("ERRO: O driver foi encontrado, mas o caminho do banco está errado.", e);
        }
    }
}
