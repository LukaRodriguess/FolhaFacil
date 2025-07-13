package folhafácil;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class FXMLDocumentController implements Initializable {

    @FXML
    private Button close;

    @FXML
    private TextField login;

    @FXML
    private Button loginBtn;

    @FXML
    private AnchorPane main_form;

    @FXML
    private PasswordField password;

    // Constantes para mensagens
    private static final String ERROR_TITLE = "Error Message";
    private static final String INFO_TITLE = "Information Message";
    private static final String CONFIRM_TITLE = "Confirmação";

    public void loginAdmin() {
        if (login.getText().isEmpty() || password.getText().isEmpty()) {
            showAlert(AlertType.ERROR, ERROR_TITLE, "Por favor, preencha todos os campos em branco");
            return;
        }
        
        String sql = "SELECT * FROM admin WHERE login = ? AND password = ?";

        try (Connection connect = database.connectDb();
             PreparedStatement prepare = connect.prepareStatement(sql)) {

            prepare.setString(1, login.getText());
            prepare.setString(2, password.getText());

            try (ResultSet result = prepare.executeQuery()) {
                if (result.next()) {
                    openDashboard();
                } else {
                    showAlert(AlertType.ERROR, ERROR_TITLE, "Login/Senha incorretos");
                }
            }
        } catch (SQLException e) {
            showAlert(AlertType.ERROR, ERROR_TITLE, "Ocorreu um erro ao acessar o banco de dados.");
        }
    }

    private void openDashboard() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("dashboard.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(root);

            // Configuração para arrastar a janela
            double[] offset = new double[2]; // Substitui x e y
            root.setOnMousePressed((MouseEvent event) -> {
                offset[0] = event.getSceneX();
                offset[1] = event.getSceneY();
            });

            root.setOnMouseDragged((MouseEvent event) -> {
                stage.setX(event.getScreenX() - offset[0]);
                stage.setY(event.getScreenY() - offset[1]);
            });

            stage.initStyle(StageStyle.TRANSPARENT);
            stage.setScene(scene);
            stage.show();

            // Fecha a janela de login
            loginBtn.getScene().getWindow().hide();
        } catch (IOException e) {
            showAlert(AlertType.ERROR, ERROR_TITLE, "Não foi possível carregar a tela de dashboard.");
        }
    }

    private void showAlert(AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void close() {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle(CONFIRM_TITLE);
        alert.setHeaderText(null);
        alert.setContentText("Tem certeza que deseja sair?");

        if (alert.showAndWait().get() == ButtonType.OK) {
            System.exit(0);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Inicialização adicional, se necessário
    }
}