package Entities;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Car {

    //Car columns
    private StringProperty carNumber;
    private IntegerProperty clientId;
    private StringProperty mark;
    private StringProperty color;

    public Car() {
        this.carNumber = new SimpleStringProperty();
        this.clientId = new SimpleIntegerProperty();
        this.mark = new SimpleStringProperty();
        this.color = new SimpleStringProperty();
    }

    public String getCarNumber() {
        return carNumber.get();
    }

    public StringProperty carNumberProperty() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber.set(carNumber);
    }

    public int getClientId() {
        return clientId.get();
    }

    public IntegerProperty clientIdProperty() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId.set(clientId);
    }

    public String getMark() {
        return mark.get();
    }

    public StringProperty markProperty() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark.set(mark);
    }

    public String getColor() {
        return color.get();
    }

    public StringProperty colorProperty() {
        return color;
    }

    public void setColor(String color) {
        this.color.set(color);
    }

}
