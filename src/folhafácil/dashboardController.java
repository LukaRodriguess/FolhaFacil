/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package folhafácil;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.scene.control.TextField;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author llukr
 */
public class dashboardController implements Initializable {
    
    @FXML
    private AnchorPane main_form;
    
    @FXML
    private Button addEmployee_addBtn;
    
    @FXML
    private Button addEmployee_btn;
    
    @FXML
    private Button addEmployee_clearBtn;
    
    @FXML
    private TableColumn<?, ?> addEmployee_col_date;
    
    @FXML
    private TableColumn<?, ?> addEmployee_col_education;
    
    @FXML
    private TableColumn<?, ?> addEmployee_col_employeeID;
    
    @FXML
    private TableColumn<?, ?> addEmployee_col_firstName;
    
    @FXML
    private TableColumn<?, ?> addEmployee_col_gender;
    
    @FXML
    private ImageView addEmployee_col_image;
    
    @FXML
    private TableColumn<?, ?> addEmployee_col_lastName;
    
    @FXML
    private TableColumn<?, ?> addEmployee_col_phoneNum;
    
    @FXML
    private TableColumn<?, ?> addEmployee_col_position;
    
    @FXML
    private TableView<?> addEmployee_col_tableView;
    
    @FXML
    private Button addEmployee_deleteBtn;
    
    @FXML
    private TextField addEmployee_employeeID;
    
    @FXML
    private TextField addEmployee_firstName;
    
    @FXML
    private AnchorPane addEmployee_form;
    
    @FXML
    private ComboBox<?> addEmployee_gender;
    
    @FXML
    private Button addEmployee_importBtn;
    
    @FXML
    private TextField addEmployee_lastName;
    
    @FXML
    private TextField addEmployee_phoneNum;
    
    @FXML
    private ComboBox<?> addEmployee_position;
    
    @FXML
    private TextField addEmployee_seach;
    
    @FXML
    private Button addEmployee_updateBtn;
    
    @FXML
    private Button close;
    
    @FXML
    private Button home_btn;
    
    @FXML
    private AnchorPane home_chart;
    
    @FXML
    private AnchorPane home_form;
    
    @FXML
    private AnchorPane home_totalEmployees;
    
    @FXML
    private AnchorPane home_totalInactiveEm;
    
    @FXML
    private AnchorPane home_totalPresents;
    
    @FXML
    private Label login;
    
    @FXML
    private Button logout;
    
    @FXML
    private Button minimize;
    
    @FXML
    private Button salary_btn;
    
    @FXML
    private Button salary_clearBtn;
    
    @FXML
    private TableColumn<?, ?> salary_col_employeeID;
    
    @FXML
    private TableColumn<?, ?> salary_col_firstName;
    
    @FXML
    private TableColumn<?, ?> salary_col_lastName;
    
    @FXML
    private TableColumn<?, ?> salary_col_position;
    
    @FXML
    private TableColumn<?, ?> salary_col_salary;
    
    @FXML
    private TextField salary_employeeID;
    
    @FXML
    private Label salary_firstName;
    
    @FXML
    private AnchorPane salary_form;
    
    @FXML
    private Label salary_lastName;
    
    @FXML
    private Label salary_position;
    
    @FXML
    private TextField salary_salary;
    
    @FXML
    private TableView<?> salary_tableView;
    
    @FXML
    private Button salary_updateBtn;

    
    private Connection connect;
    private Statement stateemn;
    private PreparedStatement prepare;
    private ResultSet result;
    
    public ObservableList<employeeData> addEmployeeListData() {
        
        ObservableList<employeeData> ListData = FXCollections.observableArrayList();
        String sql = "SELECT * FROM employee";
        
        connect = database.connectDb();
        
        try{
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            employeeData employeeD;
            
            while(result.next()) {
                employeeD = new employeeData(result.getInt("employee_id")
                        ,  result.getString("firstName")
                        , result.getString("lastName")
                        , result.getString("gender")
                        , result.getString("phoneNum")
                        , result.getString("position")
                        , result.getString("image")
                        , result.getDate("date"));
                
            }
            
        }catch(Exception e) {e.printStackTrace();}
        
    
    public void displayUsername() {
        login.setText(getData.username);
    }
    
    public void switchForm(ActionEvent event) {
        
        if (event.getSource() == home_btn) {
            home_form.setVisible(true);
            addEmployee_form.setVisible(false);
            salary_form.setVisible(false);
            
            home_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #3b4368, #28966c);");
            addEmployee_btn.setStyle("-fx-backgound-color:transparent");
            salary_btn.setStyle("-fx-backgound-color:transparent");
            
        } else if (event.getSource() == addEmployee_btn) {
            home_form.setVisible(false);
            addEmployee_form.setVisible(true);
            salary_form.setVisible(false);
            
            addEmployee_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #3b4368, #28966c);");
            home_btn.setStyle("-fx-backgound-color:transparent");
            salary_btn.setStyle("-fx-backgound-color:transparent");
            
        } else if (event.getSource() == salary_btn) {
            home_form.setVisible(false);
            addEmployee_form.setVisible(false);
            salary_form.setVisible(true);
           
            salary_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #3b4368, #28966c);");
            addEmployee_btn.setStyle("-fx-backgound-color:transparent");
            home_btn.setStyle("-fx-backgound-color:transparent");
            
        }
        
    }
    
    private double x = 0;
    private double y = 0;
    
    public void logout() {
        
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Message");
        alert.setHeaderText(null);
        alert.setContentText("Você tem certeza de que deseja sair");
        Optional<ButtonType> option = alert.showAndWait();
        
        try {
            
            if (option.get().equals(ButtonType.OK)) {
                
                logout.getScene().getWindow().hide();
                Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(root);
                
                root.setOnMousePressed((MouseEvent event) -> {
                    x = event.getSceneX();
                    y = event.getSceneY();
                });
                
                root.setOnMouseDragged((MouseEvent event) -> {
                    stage.setX(event.getScreenX() - x);
                    stage.setY(event.getScreenY() - y);
                    
                    stage.setOpacity(.8);
                });
                
                root.setOnMouseReleased((MouseEvent event) -> {
                    stage.setOpacity(1);
                });
                
                stage.initStyle(StageStyle.TRANSPARENT);
                
                stage.setScene(scene);
                stage.show();
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void close() {
        System.exit(0);
    }
    
    public void minimize() {
        Stage stage = (Stage) main_form.getScene().getWindow();
        stage.setIconified(true);
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
    
}
