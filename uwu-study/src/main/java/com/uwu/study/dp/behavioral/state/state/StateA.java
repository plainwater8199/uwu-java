package com.uwu.study.dp.behavioral.state.state;


import com.uwu.study.dp.behavioral.state.Context;

public class StateA implements State{
    @Override
    public void handle(Context context) {
        int count  = context.getCount();

        if(count >= 1){
            System.out.println("购买成功！");
            context.setCount(count - 1);

            if(context.getCount() == 0){
                context.setState(new StateB());
            }
        }else{
            System.out.println("购买失败");
        }
    }
}
