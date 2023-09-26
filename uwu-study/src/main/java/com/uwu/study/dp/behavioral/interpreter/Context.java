package com.uwu.study.dp.behavioral.interpreter;


import com.uwu.study.dp.behavioral.interpreter.expression.NonTerminalExpression;
import com.uwu.study.dp.behavioral.interpreter.expression.TerminalExpression;

public class Context {
    private String[] regions = {"A区","B区","C区"};
    private String[] persons = {"开发人员","测试人员","调试人员"};

    private NonTerminalExpression nonTerminal;

    public Context(){
        TerminalExpression region = new TerminalExpression(regions);
        TerminalExpression person = new TerminalExpression(persons);
        nonTerminal = new NonTerminalExpression(region,person);
    }

    public void check(String info){
        boolean check = nonTerminal.interpret(info);
        if(check){
            System.out.println("识别成功");
        }else{
            System.out.println("识别失败");
        }
    }
}
