package com.example.demo.model;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

@XmlRootElement(name = "cars")
public class CarsWrapper {
    @XmlElement(name = "car")
    public List<Car> car;

    public CarsWrapper() {}

    public CarsWrapper(List<Car> car) {
        this.car = car;
    }
}