package com.uwu.study.dp.behavioral.state.state;


import com.uwu.study.dp.behavioral.state.Context;

public class StateB implements State{
    @Override
    public void handle(Context context) {
        int count = context.getCount();

        if(count == 0){
            System.out.println("购买失败！等待补货！");

            context.setCount(5);

            System.out.println("补货成功！请重新购买！");
            context.setState(new StateA());
        }
    }
}
