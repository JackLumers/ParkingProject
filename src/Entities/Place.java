package Entities;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Place {

    //Declare Employees Table Columns
    private IntegerProperty id;
    private StringProperty placeName;
    private StringProperty name;
    private StringProperty phoneNumber;
    private StringProperty endDate;
    private IntegerProperty price;
    private IntegerProperty sum;
    private StringProperty carNumber;

    //Constructor
    public Place() {
        this.id = new SimpleIntegerProperty();
        this.placeName = new SimpleStringProperty();
        this.name = new SimpleStringProperty();
        this.endDate = new SimpleStringProperty();
        this.phoneNumber = new SimpleStringProperty();
        this.carNumber = new SimpleStringProperty();
        this.price = new SimpleIntegerProperty();
        this.sum = new SimpleIntegerProperty();
    }

    public String getEndDate() {
        return endDate.get();
    }

    public StringProperty endDateProperty() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate.set(endDate);
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

    public String getPlaceName() {
        return placeName.get();
    }

    public StringProperty placeNameProperty() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName.set(placeName);
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

    public String getPhoneNumber() {
        return phoneNumber.get();
    }

    public StringProperty phoneNumberProperty() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber.set(phoneNumber);
    }

    public int getPrice() {
        return price.get();
    }

    public IntegerProperty priceProperty() {
        return price;
    }

    public void setPrice(int price) {
        this.price.set(price);
    }

    public int getSum() {
        return sum.get();
    }

    public IntegerProperty sumProperty() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum.set(sum);
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
}
