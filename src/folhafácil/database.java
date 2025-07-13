package folhafácil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe responsável por gerenciar a conexão com o banco de dados.
 */
public class database {

    /**
     * Método para estabelecer a conexão com o banco de dados MySQL.
     *
     * @return Connection objeto de conexão com o banco de dados.
     */
    public static Connection connectDb() {
        try {
            // Carrega o driver JDBC do MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Configura a URL de conexão com o banco de dados
            String url = "jdbc:mysql://localhost:3306/employee?useSSL=false&serverTimezone=UTC";
            String user = "root"; // Nome de usuário do banco de dados
            String password = ""; // Senha do banco de dados

            // Estabelece a conexão
            Connection connect = DriverManager.getConnection(url, user, password);
            return connect;
        } catch (ClassNotFoundException e) {
            System.err.println("Erro: Driver JDBC do MySQL não encontrado!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Erro ao conectar ao banco de dados!");
            e.printStackTrace();
        }
        return null; // Retorna null em caso de falha na conexão
    }
}