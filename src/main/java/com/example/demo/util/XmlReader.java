package com.example.demo.util;

import com.example.demo.model.Car;
import com.example.demo.model.CarCsvInfo;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;
import java.util.*;


public class XmlReader {

    public static List<Car> readXml(String fileName, List<CarCsvInfo> csvInfoList) throws Exception {
        File xmlFile = new File("src/main/resources/" + fileName);
        DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document doc = db.parse(xmlFile);
        NodeList carNodes = doc.getElementsByTagName("car");

        List<Car> cars = new ArrayList<>();

        for (int i = 0; i < carNodes.getLength(); i++) {
            Element carElem = (Element) carNodes.item(i);
            String model = carElem.getElementsByTagName("model").item(0).getTextContent();
            String type = carElem.getElementsByTagName("type").item(0).getTextContent();
            double usd = Double.parseDouble(carElem.getElementsByTagName("price").item(0).getTextContent());

            double eur = 0, jpy = 0;
            NodeList prices = ((Element) carElem.getElementsByTagName("prices").item(0)).getElementsByTagName("price");
            for (int j = 0; j < prices.getLength(); j++) {
                Element p = (Element) prices.item(j);
                String currency = p.getAttribute("currency");
                double val = Double.parseDouble(p.getTextContent());
                if (currency.equals("EUR")) eur = val;
                if (currency.equals("JPY")) jpy = val;
            }

            // Get brand and release date from CSV (same index)
            CarCsvInfo info = i < csvInfoList.size()
                    ? csvInfoList.get(i)
                    : new CarCsvInfo("Unknown", 2020, 1, 1);

            cars.add(new Car(
                    info.brand,
                    model,
                    type,
                    usd,
                    eur,
                    jpy,
                    info.year,
                    info.month,
                    info.day
            ));
        }

        return cars;
    }
}

