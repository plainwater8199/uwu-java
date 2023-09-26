package com.uwu.study.dp.create.build;

import java.util.ArrayList;
import java.util.List;

public class Product {
    List<String> parts = new ArrayList<>();

    public void add(String part){
        parts.add(part);
    }

    public void show(){
        System.out.print("产品的组成：");
        for(String s:parts){
            System.out.print(s+" ");
        }
        System.out.print("\n");
    }
}
