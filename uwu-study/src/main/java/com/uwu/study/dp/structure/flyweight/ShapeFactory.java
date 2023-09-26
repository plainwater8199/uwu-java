package com.uwu.study.dp.structure.flyweight;

import java.util.HashMap;
import java.util.Map;

public class ShapeFactory {
    private Map<String,Shape> map = new HashMap<>();

    public Shape getShape(String key){
        if(!map.containsKey(key)){
            map.put(key,new Circle(key));
            System.out.println("create color: "+key+" circle");
        }
        return map.get(key);
    }
}
