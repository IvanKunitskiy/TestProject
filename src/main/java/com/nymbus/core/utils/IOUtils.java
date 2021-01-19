package com.nymbus.core.utils;

import au.com.bytecode.opencsv.CSVWriter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class IOUtils {

    public void createWeilandFile(List<String> accounts, double amount){
        StringBuilder file = new StringBuilder();
        for (String account : accounts) {
            file.append(account);
            file.append("          ");
            file.append("530000000").append(amount);
            file.append("          ");
            file.append("Analysis Charges AutoTest\n");
        }
        file.append("1111100");
        file.append("          ");
        file.append("4000000009.95");
        file.append("          ");
        file.append("Analysis Charges AutoTest\n");

        String[] record = file.toString().split("\n");
        String filepath = "src/main/resources/client/weiland.IF3";
        try (CSVWriter writer = new CSVWriter(new FileWriter(filepath), '\n',' ',' ')) {
            writer.writeNext(record);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void writeToFile(String filepath, String string) {
        String[] record = string.split("\\|");
        try (CSVWriter writer = new CSVWriter(new FileWriter(filepath), '|')) {
            writer.writeNext(record);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String readFromFile(String filepath) {
        String companyName = "";
        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            companyName = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            companyName = "empty file";
        }
        return companyName;
    }
}
