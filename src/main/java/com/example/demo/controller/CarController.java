package com.example.demo.controller;

import com.example.demo.model.*;
import com.example.demo.service.CarService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cars")
public class CarController {
    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping(produces = {"application/json", "application/xml", "text/plain"})
    public ResponseEntity<?> getCars(
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) Double price,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Integer month,
            @RequestParam(required = false) Integer day,
            @RequestParam(defaultValue = "YEAR") SortBy sort,
            @RequestParam(defaultValue = "DESC") SortDirection direction,
            @RequestParam(defaultValue = "JSON") OutputFormat format
    ) {
        List<Car> cars = carService.filterAndSort(brand, price, year, month, day, sort, direction);

        return switch (format) {
            case JSON -> ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(cars);
            case XML -> ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_XML)
                    .body(new CarsWrapper(cars));  // <- wrapper object for List<Car>
            case TABLE -> ResponseEntity.ok().contentType(MediaType.TEXT_PLAIN)
                    .body(String.join("\n", cars.stream().map(Car::toTableString).toList()));
        };
    }
}