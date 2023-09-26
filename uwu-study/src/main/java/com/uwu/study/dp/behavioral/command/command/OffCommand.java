package com.uwu.study.dp.behavioral.command.command;


import com.uwu.study.dp.behavioral.command.Tv;

public class OffCommand implements Command{

    private Tv tv;

    public OffCommand(Tv tv){
        this.tv = tv;
    }
    @Override
    public void Execute() {
        tv.OffAction();
    }
}
