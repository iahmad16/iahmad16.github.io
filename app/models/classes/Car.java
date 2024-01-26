package models.classes;

import models.enums.*;
import scala.Int;

import java.util.List;

public class Car {

    public String listingID;
    public Long listingDate;
    public Customer customer;
    public String carID;
    public String make;
    public String model;
    public String subClass;
    public Integer modelYear;
    public Long mileage;
    public CarGearType gearType;
    public CarFuelType fuelType;
    public CarBodyType bodyType;
    public Boolean underWarranty;
    public Integer warrantyExpiry;
    public String exteriorColor;
    public String interiorColor;
    public CarSeatType seatType;
    public CarWheelDrive wheelDrive;
    public Boolean hasSunroof;
    public String importCountry;
    public Long price;
    public String currency;
    public CarListingStatus status;
    public Integer views;
    public Long soldDate;
    public Long soldPrice;
    public List<String> images;

    public Car(String listingID, Long listingDate, Customer customer, String carID, String make, String model,
               String subClass, Integer modelYear, Long mileage, CarGearType gearType, CarFuelType fuelType,
               CarBodyType bodyType, Boolean underWarranty, Integer warrantyExpiry, String exteriorColor,
               String interiorColor, CarSeatType seatType, CarWheelDrive wheelDrive, Boolean hasSunroof,
               String importCountry, Long price, String currency, CarListingStatus status, Integer views,
               Long soldDate, Long soldPrice, List<String> images) {

        this.listingID = listingID;
        this.listingDate = listingDate;
        this.customer = customer;
        this.carID = carID;
        this.make = make;
        this.model = model;
        this.subClass = subClass;
        this.modelYear = modelYear;
        this.mileage = mileage;
        this.gearType = gearType;
        this.fuelType = fuelType;
        this.bodyType = bodyType;
        this.underWarranty = underWarranty;
        this.warrantyExpiry = warrantyExpiry;
        this.exteriorColor = exteriorColor;
        this.interiorColor = interiorColor;
        this.seatType = seatType;
        this.wheelDrive = wheelDrive;
        this.hasSunroof = hasSunroof;
        this.importCountry = importCountry;
        this.price = price;
        this.currency = currency;
        this.status = status;
        this.views = views;
        this.soldDate = soldDate;
        this.soldPrice = soldPrice;
        this.images = images;
    }

}
