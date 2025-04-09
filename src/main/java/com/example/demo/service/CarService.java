package com.example.demo.service;

import com.example.demo.model.Car;
import com.example.demo.model.CarCsvInfo;
import com.example.demo.model.SortBy;
import com.example.demo.model.SortDirection;
import com.example.demo.util.CsvReader;
import com.example.demo.util.XmlReader;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.List;

import java.util.*;

@Service
public class CarService {
    private List<Car> allCars = new ArrayList<>();

    @PostConstruct
    public void init() throws Exception {
        List<CarCsvInfo> infoList = CsvReader.readCarCsvInfo("CarsBrand.csv");
        allCars = XmlReader.readXml("carsType.xml", infoList);
    }

    public List<Car> filterAndSort(String brand, Double maxPrice, Integer year, Integer month, Integer day,
                                   SortBy sortBy, SortDirection direction) {

        Comparator<Car> comparator = switch (sortBy) {
            case PRICE -> Comparator.comparingDouble(c -> c.priceUSD);
            case YEAR -> Comparator.comparingInt(c -> c.releaseYear);
        };
        if (direction == SortDirection.DESC) comparator = comparator.reversed();

        return allCars.stream()
                .filter(c -> brand == null || c.brand.equalsIgnoreCase(brand))
                .filter(c -> maxPrice == null || c.priceUSD <= maxPrice)
                .filter(c -> year == null || (c.releaseYear == year && c.releaseMonth == month && c.releaseDay == day))
                .sorted(comparator)
                .toList();
    }
}
