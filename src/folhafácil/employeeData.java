package folhafácil; // evite acento em nomes de pacote (mude a pasta também)

import java.sql.Date;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class employeeData {

    private final IntegerProperty employeeId;
    private final StringProperty firstName;
    private final StringProperty lastName;
    private final StringProperty gender;
    private final StringProperty phoneNum;
    private final StringProperty position;
    private final StringProperty image;
    private final ObjectProperty<Date> date;

    // mantém como Double simples; se quiser binding na UI, podemos trocar por DoubleProperty
    private Double salary;

    // Construtor completo
    public employeeData(Integer employeeId,
                        String firstName,
                        String lastName,
                        String gender,
                        String phoneNum,
                        String position,
                        String image,
                        Date date) {

        this.employeeId = new SimpleIntegerProperty(employeeId == null ? 0 : employeeId);
        this.firstName  = new SimpleStringProperty(firstName);
        this.lastName   = new SimpleStringProperty(lastName);
        this.gender     = new SimpleStringProperty(gender);
        this.phoneNum   = new SimpleStringProperty(phoneNum);
        this.position   = new SimpleStringProperty(position);
        this.image      = new SimpleStringProperty(image);
        this.date       = new SimpleObjectProperty<>(date);
        this.salary     = null; // pode ser definido depois
    }

    // Construtor resumido (precisa inicializar TODOS os campos final)
    public employeeData(Integer employeeId,
                        String firstName,
                        String lastName,
                        String position,
                        Double salary) {

        this.employeeId = new SimpleIntegerProperty(employeeId == null ? 0 : employeeId);
        this.firstName  = new SimpleStringProperty(firstName);
        this.lastName   = new SimpleStringProperty(lastName);
        this.gender     = new SimpleStringProperty(null);
        this.phoneNum   = new SimpleStringProperty(null);
        this.position   = new SimpleStringProperty(position);
        this.image      = new SimpleStringProperty(null);
        this.date       = new SimpleObjectProperty<>(null);
        this.salary     = salary; // não use new Double(salary)
    }

    public Integer getEmployeeId() { return employeeId.get(); }
    public String  getFirstName()  { return firstName.get(); }
    public String  getLastName()   { return lastName.get(); }
    public String  getGender()     { return gender.get(); }
    public String  getPhoneNum()   { return phoneNum.get(); }
    public String  getPosition()   { return position.get(); }
    public String  getImage()      { return image.get(); }
    public Date    getDate()       { return date.get(); }
    public Double  getSalary()     { return salary; }

    
    public IntegerProperty employeeIdProperty() { return employeeId; }
    public StringProperty  firstNameProperty()  { return firstName; }
    public StringProperty  lastNameProperty()   { return lastName; }
    public StringProperty  genderProperty()     { return gender; }
    public StringProperty  phoneNumProperty()   { return phoneNum; }
    public StringProperty  positionProperty()   { return position; }
    public StringProperty  imageProperty()      { return image; }
    public ObjectProperty<Date> dateProperty()  { return date; }

    
    public void setEmployeeId(Integer v) { this.employeeId.set(v == null ? 0 : v); }
    public void setFirstName(String v)   { this.firstName.set(v); }
    public void setLastName(String v)    { this.lastName.set(v); }
    public void setGender(String v)      { this.gender.set(v); }
    public void setPhoneNum(String v)    { this.phoneNum.set(v); }
    public void setPosition(String v)    { this.position.set(v); }
    public void setImage(String v)       { this.image.set(v); }
    public void setDate(Date v)          { this.date.set(v); }
    public void setSalary(Double v)      { this.salary = v; }

    @Override
    public String toString() {
        return "EmployeeData{" +
               "id=" + getEmployeeId() +
               ", name='" + getFirstName() + " " + getLastName() + '\'' +
               ", position='" + getPosition() + '\'' +
               ", salary=" + getSalary() +
               '}';
    }
}
