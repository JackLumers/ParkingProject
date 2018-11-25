package Entities;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Client {

    //Client columns
    private IntegerProperty id;
    private StringProperty name;
    private StringProperty phone;
    private StringProperty passport;

    public Client() {
        this.id = new SimpleIntegerProperty();
        this.name = new SimpleStringProperty();
        this.phone = new SimpleStringProperty();
        this.passport = new SimpleStringProperty();
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getPhone() {
        return phone.get();
    }

    public StringProperty phoneProperty() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone.set(phone);
    }

    public String getPassport() {
        return passport.get();
    }

    public StringProperty passportProperty() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport.set(passport);
    }

}
