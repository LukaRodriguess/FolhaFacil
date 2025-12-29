/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package folhafácil;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.File;
import javafx.scene.control.TextField;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
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
    private ImageView addEmployee_col_image;

    @FXML
    private TableColumn<employeeData, String> addEmployee_col_date;

    @FXML
    private TableColumn<employeeData, Number> addEmployee_col_employeeID;

    @FXML
    private TableColumn<employeeData, String> addEmployee_col_firstName;

    @FXML
    private TableColumn<employeeData, String> addEmployee_col_gender;

    @FXML
    private TableColumn<employeeData, String> addEmployee_col_lastName;

    @FXML
    private TableColumn<employeeData, String> addEmployee_col_phoneNum;

    @FXML
    private TableColumn<employeeData, String> addEmployee_col_position;

    @FXML
    private TableView<employeeData> addEmployee_col_tableView;

    @FXML
    private Button addEmployee_deleteBtn;

    @FXML
    private TextField addEmployee_employeeID;

    @FXML
    private TextField addEmployee_firstName;

    @FXML
    private AnchorPane addEmployee_form;

    @FXML
    private ComboBox<String> addEmployee_gender;

    @FXML
    private Button addEmployee_importBtn;

    @FXML
    private TextField addEmployee_lastName;

    @FXML
    private TextField addEmployee_phoneNum;

    @FXML
    private ComboBox<String> addEmployee_position;

    @FXML
    private TextField addEmployee_search;

    @FXML
    private Button addEmployee_updateBtn;

    @FXML
    private Button close;

    @FXML
    private Button home_btn;

    @FXML
    private BarChart<?, ?> home_chart;

    @FXML
    private AnchorPane home_form;

    @FXML
    private Label home_totalEmployees;

    @FXML
    private Label home_totalInactiveEm;

    @FXML
    private Label home_totalPresents;

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
    private TableColumn<employeeData, String> salary_col_employeeID;

    @FXML
    private TableColumn<employeeData, String> salary_col_firstName;

    @FXML
    private TableColumn<employeeData, String> salary_col_lastName;

    @FXML
    private TableColumn<employeeData, String> salary_col_position;

    @FXML
    private TableColumn<employeeData, String> salary_col_salary;

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
    private TableView<employeeData> salary_tableView;

    @FXML
    private Button salary_updateBtn;
    private Connection connect;
    private Statement statement;
    private PreparedStatement prepare;
    private ResultSet result;

    private Image image;

    private ObservableList<employeeData> addEmployeeList;

    @FXML
    private TableView<?> addEmployee_tableView;

    public void homeTotalEmployees() {

        String sql = "SELECT COUNT(id) FROM employee_info";
        connect = database.connectDb();
        int countData = 0;
        try {

            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()) {
                countData = result.getInt("COUNT(id)");
            }

            home_totalEmployees.setText(String.valueOf(countData));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void addEmployeeTotalPresent() {

String sql = "SELECT COUNT(id) FROM employee_info WHERE salary > 0.0";

        connect = database.connectDb();
        int countData = 0;
        try {
            statement = connect.createStatement();
            result = statement.executeQuery(sql);

            while (result.next()) {
                countData = result.getInt("COUNT(id)");
            }

            home_totalPresents.setText(String.valueOf(countData));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void homeTotalInactive() {

        String sql = "SELECT COUNT(id) FROM employee_info WHERE salary = '0.0'";

        connect = database.connectDb();
        int countData = 0;
        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()) {
                countData = result.getInt("COUNT(id)");
            }
            home_totalInactiveEm.setText(String.valueOf(countData));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void homeChart() {

        home_chart.getData().clear();

        String sql = "SELECT date, COUNT(id) FROM employee GROUP BY date ORDER BY TIMESTAMP(date) ASC LIMIT 7";
        connect = database.connectDb();

        try {
            XYChart.Series chart = new XYChart.Series();

            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()) {
                chart.getData().add(new XYChart.Data(result.getString(1), result.getInt(2)));
            }

            home_chart.getData().add(chart);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void addEmployeeAdd() {

        if (addEmployee_employeeID.getText().trim().isEmpty()
                || addEmployee_firstName.getText().trim().isEmpty()
                || addEmployee_lastName.getText().trim().isEmpty()
                || addEmployee_gender.getSelectionModel().getSelectedItem() == null
                || addEmployee_phoneNum.getText().trim().isEmpty()
                || addEmployee_position.getSelectionModel().getSelectedItem() == null
                || getData.path == null || getData.path.isEmpty()) {

            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Mensagem de Erro");
            alert.setHeaderText(null);
            alert.setContentText("Por favor, preencha todos os campos em branco");
            alert.showAndWait();
            return;
        }

        int employeeId;
        try {
            employeeId = Integer.parseInt(addEmployee_employeeID.getText().trim());
        } catch (NumberFormatException nfe) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Mensagem de Erro");
            alert.setHeaderText(null);
            alert.setContentText("Employee ID precisa ser numérico.");
            alert.showAndWait();
            return;
        }

        connect = database.connectDb();
        if (connect == null) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erro de Conexão");
            alert.setHeaderText(null);
            alert.setContentText("Não foi possível conectar ao banco.");
            alert.showAndWait();
            return;
        }

        try {
            final String checkSql = "SELECT 1 FROM employee WHERE employee_id = ?";
            try (PreparedStatement psCheck = connect.prepareStatement(checkSql)) {
                psCheck.setInt(1, employeeId);
                try (ResultSet rs = psCheck.executeQuery()) {
                    if (rs.next()) {
                        Alert alert = new Alert(AlertType.ERROR);
                        alert.setTitle("Mensagem de Erro");
                        alert.setHeaderText(null);
                        alert.setContentText("Employee ID: " + employeeId + " já existe!");
                        alert.showAndWait();
                        return;
                    }
                }
            }

            int nextId = 1;
            try (Statement st = connect.createStatement();
                    ResultSet rs = st.executeQuery("SELECT COALESCE(MAX(id),0)+1 AS nextId FROM employee")) {
                if (rs.next()) {
                    nextId = rs.getInt(1);
                }
            }

            Date date = new Date();
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());

            final String insertSql
                    = "INSERT INTO employee (id, employee_id, firstName, lastName, gender, phoneNum, position, image, `date`) "
                    + "VALUES (?,?,?,?,?,?,?,?,?)";

            try (PreparedStatement psInsert = connect.prepareStatement(insertSql)) {
                psInsert.setInt(1, nextId);
                psInsert.setInt(2, employeeId);
                psInsert.setString(3, addEmployee_firstName.getText().trim());
                psInsert.setString(4, addEmployee_lastName.getText().trim());
                psInsert.setString(5, addEmployee_gender.getSelectionModel().getSelectedItem());
                psInsert.setString(6, addEmployee_phoneNum.getText().trim());
                psInsert.setString(7, addEmployee_position.getSelectionModel().getSelectedItem());

                String imageName = "";
                if (getData.path != null && !getData.path.isEmpty()) {
                    File sourceFile = new File(getData.path);
                    if (sourceFile.exists()) {

                        File destDir = new File("employee_images");
                        if (!destDir.exists()) {
                            destDir.mkdir();
                        }

                        String uniqueName = employeeId + "_" + sourceFile.getName();
                        File destFile = new File(destDir, uniqueName);

                        java.nio.file.Files.copy(
                                sourceFile.toPath(),
                                destFile.toPath(),
                                java.nio.file.StandardCopyOption.REPLACE_EXISTING
                        );

                        imageName = uniqueName;
                        getData.path = destFile.getAbsolutePath();
                    }
                }

                psInsert.setString(8, imageName);
                psInsert.setDate(9, sqlDate);

                psInsert.executeUpdate();
            }

            String insertInfo = "INSERT INTO employee_info"
                    + "(id, employee_id,firstName,lastName,position,salary,date) "
                    + "VALUES(?,?,?,?,?,?,?)";

            try (PreparedStatement psInsert = connect.prepareStatement(insertInfo)) {
                psInsert.setInt(1, nextId);
                psInsert.setInt(2, employeeId);
                psInsert.setString(3, addEmployee_firstName.getText().trim());
                psInsert.setString(4, addEmployee_lastName.getText().trim());
                psInsert.setString(5, addEmployee_position.getSelectionModel().getSelectedItem());
                psInsert.setDouble(6, 0.0);
                psInsert.setDate(7, sqlDate);

                psInsert.executeUpdate();
            }

            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Mensagem de Informação");
            alert.setHeaderText(null);
            alert.setContentText("Adicionado com sucesso");
            alert.showAndWait();

            addEmployeeShowListData();
            salaryShowListData();
            addEmployeeReset();

        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText(null);
            alert.setContentText("Erro ao salvar funcionário: " + e.getMessage());
            alert.showAndWait();
        }
    }

    public void addEmployeeUpdate() {

        if (addEmployee_employeeID.getText().trim().isEmpty()
                || addEmployee_firstName.getText().trim().isEmpty()
                || addEmployee_lastName.getText().trim().isEmpty()
                || addEmployee_gender.getSelectionModel().getSelectedItem() == null
                || addEmployee_phoneNum.getText().trim().isEmpty()
                || addEmployee_position.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Mensagem de Erro");
            alert.setHeaderText(null);
            alert.setContentText("Por favor, preencha os campos obrigatórios.");
            alert.showAndWait();
            return;
        }

        int employeeId;
        try {
            employeeId = Integer.parseInt(addEmployee_employeeID.getText().trim());
        } catch (NumberFormatException e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Mensagem de Erro");
            alert.setHeaderText(null);
            alert.setContentText("Funcionário ID precisa ser numérico.");
            alert.showAndWait();
            return;
        }

        connect = database.connectDb();
        if (connect == null) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erro de Conexão");
            alert.setHeaderText(null);
            alert.setContentText("Não foi possível conectar ao banco.");
            alert.showAndWait();
            return;
        }

        try {
            String checkSql = "SELECT COUNT(*) FROM employee WHERE employee_id = ?";
            PreparedStatement checkStmt = connect.prepareStatement(checkSql);
            checkStmt.setInt(1, employeeId);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next() && rs.getInt(1) == 0) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Mensagem de Erro");
                alert.setHeaderText(null);
                alert.setContentText("Funcionário ID " + employeeId + " não existe! Use 'Adicionar' para criar um novo funcionário.");
                alert.showAndWait();
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Mensagem de Confirmação");
        alert.setHeaderText(null);
        alert.setContentText("Você tem certeza de que deseja ATUALIZAR o Funcionário ID: " + employeeId + "?");
        Optional<ButtonType> option = alert.showAndWait();

        try {
            if (option.isPresent() && option.get() == ButtonType.OK) {

                boolean temNovaImagem = (getData.path != null && !getData.path.trim().isEmpty());

                String sql;
                if (temNovaImagem) {
                    File sourceFile = new File(getData.path);
                    if (sourceFile.exists()) {
                        File destDir = new File("employee_images");
                        if (!destDir.exists()) {
                            destDir.mkdir();
                        }

                        String uniqueName = employeeId + "_" + sourceFile.getName();
                        File destFile = new File(destDir, uniqueName);

                        java.nio.file.Files.copy(
                                sourceFile.toPath(),
                                destFile.toPath(),
                                java.nio.file.StandardCopyOption.REPLACE_EXISTING
                        );

                        sql = "UPDATE employee SET firstName = '" + addEmployee_firstName.getText()
                                + "', lastName = '" + addEmployee_lastName.getText()
                                + "', gender = '" + addEmployee_gender.getSelectionModel().getSelectedItem()
                                + "', phoneNum = '" + addEmployee_phoneNum.getText()
                                + "', position = '" + addEmployee_position.getSelectionModel().getSelectedItem()
                                + "', image = '" + uniqueName
                                + "', date = '" + new java.sql.Date(System.currentTimeMillis())
                                + "' WHERE employee_id = '" + employeeId + "'";
                    } else {
                        sql = "UPDATE employee SET firstName = '" + addEmployee_firstName.getText()
                                + "', lastName = '" + addEmployee_lastName.getText()
                                + "', gender = '" + addEmployee_gender.getSelectionModel().getSelectedItem()
                                + "', phoneNum = '" + addEmployee_phoneNum.getText()
                                + "', position = '" + addEmployee_position.getSelectionModel().getSelectedItem()
                                + "', date = '" + new java.sql.Date(System.currentTimeMillis())
                                + "' WHERE employee_id = '" + employeeId + "'";
                    }
                } else {
                    sql = "UPDATE employee SET firstName = '" + addEmployee_firstName.getText()
                            + "', lastName = '" + addEmployee_lastName.getText()
                            + "', gender = '" + addEmployee_gender.getSelectionModel().getSelectedItem()
                            + "', phoneNum = '" + addEmployee_phoneNum.getText()
                            + "', position = '" + addEmployee_position.getSelectionModel().getSelectedItem()
                            + "', date = '" + new java.sql.Date(System.currentTimeMillis())
                            + "' WHERE employee_id = '" + employeeId + "'";
                }

                statement = connect.createStatement();
                int rowsUpdated = statement.executeUpdate(sql);

                double salary = 0;

                String checkData = "SELECT * FROM employee_info WHERE employee_id = '"
                        + addEmployee_employeeID.getText() + "'";

                prepare = connect.prepareStatement(checkData);
                result = prepare.executeQuery();

                while (result.next()) {
                    salary = result.getDouble("salary");
                }

                if (rowsUpdated > 0) {
                    String updateInfo = "UPDATE employee_info SET firstName = '"
                            + addEmployee_firstName.getText() + "', lastName = '"
                            + addEmployee_lastName.getText() + "', position = '"
                            + addEmployee_position.getSelectionModel().getSelectedItem()
                            + "' WHERE employee_id = '" + employeeId + "'";

                    prepare = connect.prepareStatement(updateInfo);
                    prepare.executeUpdate();

                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Mensagem de Informação");
                    alert.setHeaderText(null);
                    alert.setContentText("Atualizado com sucesso!");
                    alert.showAndWait();

                    addEmployeeShowListData();
                    salaryShowListData();
                    addEmployeeReset();
                } else {
                    alert = new Alert(AlertType.WARNING);
                    alert.setTitle("Aviso");
                    alert.setHeaderText(null);
                    alert.setContentText("Nenhum registro foi atualizado.");
                    alert.showAndWait();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText(null);
            alert.setContentText("Erro ao atualizar: " + e.getMessage());
            alert.showAndWait();
        }
    }

    public void addEmployeeDetele() {
        if (addEmployee_employeeID.getText().trim().isEmpty()) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Mensagem de Erro");
            alert.setHeaderText(null);
            alert.setContentText("Informe o Employee ID para deletar.");
            alert.showAndWait();
            return;
        }

        String employeeId = addEmployee_employeeID.getText().trim();

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Mensagem de Confirmação");
        alert.setHeaderText(null);
        alert.setContentText("Você tem certeza de que deseja DELETAR o Funcionário ID: " + employeeId + "?");
        Optional<ButtonType> option = alert.showAndWait();

        try {
            if (option.get().equals(ButtonType.OK)) {
                connect = database.connectDb();

                String deleteInfo = "DELETE FROM employee_info WHERE employee_id = '" + employeeId + "'";
                statement = connect.createStatement();
                statement.executeUpdate(deleteInfo);

                String deleteEmployee = "DELETE FROM employee WHERE employee_id = '" + employeeId + "'";
                statement.executeUpdate(deleteEmployee);

                alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Mensagem de Informação");
                alert.setHeaderText(null);
                alert.setContentText("Deletado com sucesso!");
                alert.showAndWait();

                addEmployeeShowListData();
                salaryShowListData();
                addEmployeeReset();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addEmployeeReset() {
        addEmployee_employeeID.setText("");
        addEmployee_firstName.setText("");
        addEmployee_lastName.setText("");
        addEmployee_gender.getSelectionModel().clearSelection();
        addEmployee_position.getSelectionModel().clearSelection();
        addEmployee_phoneNum.setText("");
        addEmployee_col_image.setImage(null);
        getData.path = "";
    }

    @FXML
    public void addEmployeeInsertImage() {
        FileChooser open = new FileChooser();
        File file = open.showOpenDialog(main_form.getScene().getWindow());

        if (file != null) {
            getData.path = file.getAbsolutePath(); // Apenas isso
            image = new Image(file.toURI().toString(), 101, 127, false, true);
            addEmployee_col_image.setImage(image);
        }
    }

    private String[] positionList = {
        "Coordenador de Marketing",
        "Web Developer(Back End)",
        "Web Developer (Front End)",
        "App Developer"
    };

    public void addEmployeePositionList() {
        List<String> listP = new ArrayList<>();
        for (String data : positionList) {
            listP.add(data);
        }
        ObservableList<String> listData = FXCollections.observableArrayList(listP);
        addEmployee_position.setItems(listData);
    }

    private String[] listGender = {"Masculino", "Feminino", "Outros"};

    public void addEmployeeGendernList() {
        List<String> listG = new ArrayList<>();
        for (String data : listGender) {
            listG.add(data);
        }
        ObservableList<String> listData = FXCollections.observableArrayList(listG);
        addEmployee_gender.setItems(listData);
    }

    public ObservableList<employeeData> addEmployeeListData() {
        ObservableList<employeeData> listData = FXCollections.observableArrayList();
        String sql = "SELECT * FROM employee";

        connect = database.connectDb();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            employeeData employeeD;

            while (result.next()) {
                employeeD = new employeeData(
                        result.getInt("employee_id"),
                        result.getString("firstName"),
                        result.getString("lastName"),
                        result.getString("gender"),
                        result.getString("phoneNum"),
                        result.getString("position"),
                        result.getString("image"),
                        result.getDate("date")
                );
                listData.add(employeeD);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;
    }

    public void addEmployeeSearch() {

        FilteredList<employeeData> filter = new FilteredList<>(addEmployeeList, e -> true);

        addEmployee_search.textProperty().addListener((observable, oldValue, newValue) -> {

            filter.setPredicate(predicateEmployeeData -> {

                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String searchKey = newValue.toLowerCase();

                if (predicateEmployeeData.getEmployeeId().toString().contains(searchKey)) {
                    return true;
                } else if (predicateEmployeeData.getFirstName().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateEmployeeData.getLastName().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateEmployeeData.getGender().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateEmployeeData.getPhoneNum().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateEmployeeData.getPosition().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateEmployeeData.getDate().toString().contains(searchKey)) {
                    return true;
                } else {
                    return false;
                }
            });
        });

        SortedList<employeeData> sortList = new SortedList<>(filter);

        sortList.comparatorProperty().bind(addEmployee_col_tableView.comparatorProperty());
        addEmployee_col_tableView.setItems(sortList);
    }

    public void addEmployeeShowListData() {
        addEmployeeList = addEmployeeListData();

        addEmployee_col_employeeID.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        addEmployee_col_firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        addEmployee_col_lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        addEmployee_col_gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        addEmployee_col_phoneNum.setCellValueFactory(new PropertyValueFactory<>("phoneNum"));
        addEmployee_col_position.setCellValueFactory(new PropertyValueFactory<>("position"));
        addEmployee_col_date.setCellValueFactory(new PropertyValueFactory<>("date"));

        addEmployee_col_tableView.setItems(addEmployeeList);
    }

    @FXML
    public void addEmployeeSelect() {
        employeeData employeeD = addEmployee_col_tableView.getSelectionModel().getSelectedItem();
        if (employeeD == null) {
            return;
        }

        addEmployee_employeeID.setText(String.valueOf(employeeD.getEmployeeId()));
        addEmployee_firstName.setText(employeeD.getFirstName());
        addEmployee_lastName.setText(employeeD.getLastName());
        addEmployee_phoneNum.setText(employeeD.getPhoneNum());

        addEmployee_gender.getSelectionModel().select(employeeD.getGender());
        addEmployee_position.getSelectionModel().select(employeeD.getPosition());

        String imageName = employeeD.getImage();
        System.out.println("DEBUG: Nome da imagem do banco: " + imageName);

        if (imageName != null && !imageName.trim().isEmpty()) {
            try {

                File imageFile = new File("employee_images/" + imageName);
                System.out.println("DEBUG: Caminho procurado: " + imageFile.getAbsolutePath());
                System.out.println("DEBUG: Arquivo existe? " + imageFile.exists());

                if (imageFile.exists()) {
                    getData.path = imageFile.getAbsolutePath();
                    String uri = imageFile.toURI().toString();
                    System.out.println("DEBUG: URI da imagem: " + uri);

                    image = new Image(uri, 101, 127, false, true);
                    addEmployee_col_image.setImage(image);
                    System.out.println("DEBUG: Imagem carregada com sucesso!");
                } else {
                    System.out.println("DEBUG ERRO: Imagem não encontrada em: employee_images/" + imageName);

                    // Tenta outros caminhos possíveis
                    String[] possiblePaths = {
                        "employee_images/" + imageName,
                        System.getProperty("user.dir") + "/employee_images/" + imageName,
                        "C:/Users/llukr/OneDrive/Documents/NetBeansProjects/FolhaFácil/employee_images/" + imageName,
                        imageName // Talvez já seja um caminho completo
                    };

                    for (String path : possiblePaths) {
                        File testFile = new File(path);
                        System.out.println("DEBUG: Testando caminho: " + path + " - Existe: " + testFile.exists());
                        if (testFile.exists()) {
                            getData.path = testFile.getAbsolutePath();
                            image = new Image(testFile.toURI().toString(), 101, 127, false, true);
                            addEmployee_col_image.setImage(image);
                            System.out.println("DEBUG: Imagem encontrada em caminho alternativo!");
                            return;
                        }
                    }

                    addEmployee_col_image.setImage(null);
                    getData.path = "";
                }
            } catch (Exception e) {
                System.out.println("DEBUG ERRO: " + e.getMessage());
                e.printStackTrace();
                addEmployee_col_image.setImage(null);
                getData.path = "";
            }
        } else {
            System.out.println("DEBUG: Nenhuma imagem associada a este funcionário");
            addEmployee_col_image.setImage(null);
            getData.path = "";
        }
    }

    public void salaryUpdate() {

        if (salary_employeeID.getText().isEmpty()
                || salary_firstName.getText().isEmpty()
                || salary_lastName.getText().isEmpty()
                || salary_position.getText().isEmpty()
                || salary_salary.getText().isEmpty()) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Mensagem de Erro");
            alert.setHeaderText(null);
            alert.setContentText("Por favor, preencha todos os campos.");
            alert.showAndWait();
            return;
        }

        try {

            String salaryText = salary_salary.getText().trim();

            salaryText = salaryText.replace(",", ".");

            double salaryValue = Double.parseDouble(salaryText);

            if (salaryValue < 0) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Mensagem de Erro");
                alert.setHeaderText(null);
                alert.setContentText("O salário não pode ser negativo.");
                alert.showAndWait();
                return;
            }

            String formattedSalary = String.format("%.2f", salaryValue);

            Alert confirmAlert = new Alert(AlertType.CONFIRMATION);
            confirmAlert.setTitle("Mensagem de Confirmação");
            confirmAlert.setHeaderText(null);
            confirmAlert.setContentText("Atualizar salário para R$ " + formattedSalary + "?");
            Optional<ButtonType> option = confirmAlert.showAndWait();

            if (option.isPresent() && option.get() == ButtonType.OK) {
                connect = database.connectDb();

                String sql = "UPDATE employee_info SET salary = ? WHERE employee_id = ?";
                prepare = connect.prepareStatement(sql);
                prepare.setDouble(1, salaryValue);
                prepare.setString(2, salary_employeeID.getText());

                int rowsUpdated = prepare.executeUpdate();

                if (rowsUpdated > 0) {
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Mensagem de Informação");
                    alert.setHeaderText(null);
                    alert.setContentText("Salário atualizado para R$ " + formattedSalary + "!");
                    alert.showAndWait();

                    salaryShowListData();
                    salaryReset();
                } else {
                    Alert alert = new Alert(AlertType.WARNING);
                    alert.setTitle("Aviso");
                    alert.setHeaderText(null);
                    alert.setContentText("Nenhum registro atualizado.");
                    alert.showAndWait();
                }
            }

        } catch (NumberFormatException e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Mensagem de Erro");
            alert.setHeaderText(null);
            alert.setContentText("Formato de salário inválido. Use números como: 2000, 2000.00, 2.000,00");
            alert.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText(null);
            alert.setContentText("Erro ao atualizar salário: " + e.getMessage());
            alert.showAndWait();
        }
    }

    public void salaryReset() {
        salary_employeeID.setText("");
        salary_firstName.setText("");
        salary_lastName.setText("");
        salary_position.setText("");
        salary_salary.setText("");
    }

    public ObservableList<employeeData> salaryListData() {

        ObservableList<employeeData> listData = FXCollections.observableArrayList();

        String sql = "SELECT * FROM employee_info";

        connect = database.connectDb();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            employeeData employeeD;

            while (result.next()) {
                employeeD = new employeeData(result.getInt("employee_id"),
                        result.getString("firstName"),
                        result.getString("lastName"),
                        result.getString("position"),
                        result.getDouble("salary"));

                listData.add(employeeD);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;
    }

    private ObservableList<employeeData> salaryList;

    public void salaryShowListData() {
        salaryList = salaryListData();

        salary_col_employeeID.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        salary_col_firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        salary_col_lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        salary_col_position.setCellValueFactory(new PropertyValueFactory<>("position"));
        salary_col_salary.setCellValueFactory(new PropertyValueFactory<>("salary"));

        salary_tableView.setItems(salaryList);

    }

    public void salarySelect() {

        employeeData employeeD = salary_tableView.getSelectionModel().getSelectedItem();
        int num = salary_tableView.getSelectionModel().getSelectedIndex();

        if ((num - 1) < -1) {
            return;
        }

        salary_employeeID.setText(String.valueOf(employeeD.getEmployeeId()));
        salary_firstName.setText(employeeD.getFirstName());
        salary_lastName.setText(employeeD.getLastName());
        salary_position.setText(employeeD.getPosition());
        salary_salary.setText(String.valueOf(employeeD.getSalary()));

    }

    public void displayUsername() {
        login.setText(getData.username);
    }

    @FXML
    public void switchForm(ActionEvent event) {

        if (event.getSource() == home_btn) {
            home_form.setVisible(true);
            addEmployee_form.setVisible(false);
            salary_form.setVisible(false);

            home_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #3b4368, #28966c);");
            addEmployee_btn.setStyle("-fx-background-color:transparent");
            salary_btn.setStyle("-fx-background-color:transparent");

            homeTotalEmployees();
            addEmployeeTotalPresent();
            homeTotalInactive();
            homeChart();

        } else if (event.getSource() == addEmployee_btn) {
            home_form.setVisible(false);
            addEmployee_form.setVisible(true);
            salary_form.setVisible(false);

            addEmployee_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #3b4368, #28966c);");
            home_btn.setStyle("-fx-backgound-color:transparent");
            salary_btn.setStyle("-fx-backgound-color:transparent");

            addEmployeeGendernList();
            addEmployeePositionList();
            addEmployeeSearch();

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

    @FXML
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

    @FXML
    public void close() {
        System.exit(0);
    }

    @FXML
    public void minimize() {
        Stage stage = (Stage) main_form.getScene().getWindow();
        stage.setIconified(true);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        displayUsername();

        homeTotalEmployees();
        addEmployeeTotalPresent();
        homeTotalInactive();
        homeChart();

        addEmployeeShowListData();
        addEmployeeGendernList();
        addEmployeePositionList();

        salaryShowListData();
    }
}
