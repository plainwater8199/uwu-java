package com.uwu.study.dp.behavioral.command;


import com.uwu.study.dp.behavioral.command.command.Command;

public class InvoKer {
    private Command command;

    public void setCommand(Command command) {
        this.command = command;
    }

    public void call(){
        command.Execute();
    }
}
