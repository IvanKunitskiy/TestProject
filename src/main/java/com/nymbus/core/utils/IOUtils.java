package com.nymbus.core.utils;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class IOUtils {

    public void createWeilandFile(List<String> accounts, double amount){
        StringBuilder file = new StringBuilder();
        for (String account : accounts) {
            file.append("0000");
            file.append(account);
            file.append("           ");
            file.append("530000000").append(amount);
            file.append("                               ");
            file.append("Analysis Charges AutoTest\n");
        }
        if (Constants.getEnvironment().equals("dev4")){
            file.append("0000000001001000");
        } else {
            file.append("0000000001111100");
        }
        file.append("          ");
        file.append("40000000009.95");
        file.append("                               ");
        file.append("Analysis Charges AutoTest\n");
        String withoutPoints = file.toString().replace(".", "");

        String[] record = withoutPoints.split("\n");
        String filepath = "src/main/resources/client/weiland.IF3";
        try (FileWriter writer = new FileWriter(filepath)) {
            writer.write(withoutPoints);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
