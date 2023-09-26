package com.uwu.study.dp.behavioral.interpreter.expression;

import java.util.HashSet;
import java.util.Set;

public class TerminalExpression implements Expression{
    private Set<String> set = new HashSet<>();

    public TerminalExpression(String[] data){
        for(String str : data){
            set.add(str);
        }
    }


    @Override
    public boolean interpret(String info) {
        return set.contains(info);
    }
}
