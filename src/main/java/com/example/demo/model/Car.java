package com.example.demo.model;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "car")
public class Car {
    @XmlElement
    public String brand;
    @XmlElement public String model;
    @XmlElement public String type;
    @XmlElement public double priceUSD;
    @XmlElement public double priceEUR;
    @XmlElement public double priceJPY;
    @XmlElement public int releaseYear;
    @XmlElement public int releaseMonth;
    @XmlElement public int releaseDay;

    public Car() {}

    public Car(String brand, String model, String type,
               double priceUSD, double priceEUR, double priceJPY,
               int releaseYear, int releaseMonth, int releaseDay) {
        this.brand = brand;
        this.model = model;
        this.type = type;
        this.priceUSD = priceUSD;
        this.priceEUR = priceEUR;
        this.priceJPY = priceJPY;
        this.releaseYear = releaseYear;
        this.releaseMonth = releaseMonth;
        this.releaseDay = releaseDay;
    }

    public String getFormattedPriceByType() {
        return switch (type) {
            case "SUV" -> priceEUR + " EUR";
            case "Sedan" -> priceJPY + " JPY";
            case "Truck" -> priceUSD + " USD";
            default -> priceUSD + " USD";
        };
    }

    public String toTableString() {
        return String.format("%-10s %-10s %-8s %-15s %04d-%02d-%02d",
                brand, model, type, getFormattedPriceByType(),
                releaseYear, releaseMonth, releaseDay);
    }
}
