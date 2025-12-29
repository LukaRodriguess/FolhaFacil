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
            Class.forName("com.mysql.cj.jdbc.Driver");


            String url = "jdbc:mysql://localhost:3306/employee?useSSL=false&serverTimezone=UTC";
            String user = "root"; 
            String password = ""; 


            Connection connect = DriverManager.getConnection(url, user, password);
            return connect;
        } catch (ClassNotFoundException e) {
            System.err.println("Erro: Driver JDBC do MySQL não encontrado!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Erro ao conectar ao banco de dados!");
            e.printStackTrace();
        }
        return null;
    }
}