package com.uwu.study.dp.behavioral.interpreter.expression;

public class NonTerminalExpression implements Expression{

    private TerminalExpression region;
    private TerminalExpression person;


    public NonTerminalExpression(TerminalExpression regin,TerminalExpression person){
        this.region = regin;
        this.person = person;
    }
    @Override
    public boolean interpret(String info) {
        String[] str = info.split("的");

        return region.interpret(str[0]) && person.interpret(str[1]);
    }
}
