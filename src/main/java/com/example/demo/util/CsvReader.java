package com.example.demo.util;

import com.example.demo.model.CarCsvInfo;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class CsvReader {
    public static List<CarCsvInfo> readCarCsvInfo(String fileName) throws IOException {
        List<CarCsvInfo> infoList = new ArrayList<>();
        Path path = Paths.get("src/main/resources/" + fileName);
        List<String> lines = Files.readAllLines(path);

        for (String line : lines.subList(1, lines.size())) { // skip header
            String[] parts = line.split(",");
            if (parts.length >= 2) {
                String brand = parts[0].trim().replace("\"", "");
                String[] dateParts = parts[1].trim().replace("\"", "").split("/"); // yyyy-MM-dd
                int year = Integer.parseInt(dateParts[2]);
                int month = Integer.parseInt(dateParts[0]);
                int day = Integer.parseInt(dateParts[1]);
                infoList.add(new CarCsvInfo(brand, year, month, day));
            }
        }

        return infoList;
    }
}