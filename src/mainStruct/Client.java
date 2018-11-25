package mainStruct;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Client {
    //Declare Employees Table Columns
    private IntegerProperty id;
    private StringProperty first_name;
    private StringProperty last_name;
    private StringProperty phone_number;
    private IntegerProperty hours;
    private IntegerProperty price;
    private IntegerProperty sum;



    //Constructor
    public Client() {
        this.id = new SimpleIntegerProperty();
        this.first_name = new SimpleStringProperty();
        this.last_name = new SimpleStringProperty();
        this.phone_number = new SimpleStringProperty();
        this.hours = new SimpleIntegerProperty();
        this.price = new SimpleIntegerProperty();
        this.sum = new SimpleIntegerProperty();
    }

    //employee_id
    public int getEmployeeId() {
        return id.get();
    }

    public void setEmployeeId(int employeeId){
        this.id.set(employeeId);
    }

    public IntegerProperty employeeIdProperty(){
        return id;
    }

    //first_name
    public String getFirstName () {
        return first_name.get();
    }

    public void setFirstName(String firstName){
        this.first_name.set(firstName);
    }

    public StringProperty firstNameProperty() {
        return first_name;
    }

    //last_name
    public String getLastName () {
        return last_name.get();
    }

    public void setLastName(String lastName){
        this.last_name.set(lastName);
    }

    public StringProperty lastNameProperty() {
        return last_name;
    }

    //hours
    public int getHoursId() {
        return hours.get();
    }

    public void setHoursId(int employeeId){
        this.hours.set(employeeId);
    }

    public IntegerProperty HoursIdProperty(){
        return hours;
    }

    //price
    public int getPriceId() {
        return price.get();
    }

    public void setPriceId(int employeeId){
        this.price.set(employeeId);
    }

    public IntegerProperty PriceIdProperty(){
        return price;
    }

    //phone_number
    public String getPhoneNumber () {
        return phone_number.get();
    }

    public void setPhoneNumber (String phoneNumber){
        this.phone_number.set(phoneNumber);
    }

    public StringProperty phoneNumberProperty() {
        return phone_number;
    }
    //phone_sum
    public int getSum () { return sum.get(); }

    public void setSum (int employeeId){
        this.price.set(employeeId);
    }

    public IntegerProperty SumProperty() {
        return sum;
    }

}
