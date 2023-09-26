package com.uwu.study.dp.behavioral.command.command;


import com.uwu.study.dp.behavioral.command.Tv;

public class OnCommand implements Command{
    private Tv tv;

    public OnCommand(Tv tv){
        this.tv = tv;
    }

    @Override
    public void Execute() {
        tv.OnAction();
    }
}
