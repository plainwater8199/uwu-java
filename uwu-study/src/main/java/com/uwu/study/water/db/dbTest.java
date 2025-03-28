package com.uwu.study.water.db;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class dbTest {

    public static void main(String[] args) {


        List<String> oldTables = getTabels("/Users/wangpeng/water/old_tables.txt");

        if(oldTables.size() > 0){
            List<String> newTables = getTabels("/Users/wangpeng/water/new_tables.txt");
            for(String name : newTables){
                if(!oldTables.contains(name)){
                    System.out.println(name);
                }
            }
        }




    }

    private static List<String> getTabels(String filePath) {
        List<String> tableNames = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                tableNames.add(line);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return tableNames;
    }
}
