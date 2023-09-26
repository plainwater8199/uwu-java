package com.uwu.study.dp.behavioral.state;


import com.uwu.study.dp.behavioral.state.state.State;
import com.uwu.study.dp.behavioral.state.state.StateA;

public class Context {
    private int count;

    private State state;

    public Context(){
        count = 3;
        state = new StateA();
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }


    public void request(){
        state.handle(this);
    }
}
