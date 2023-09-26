package com.uwu.study.dp.behavioral.chainofresponsibility.handler;

public class YuanZhang extends Handler{
    @Override
    public void handlerRequest(int request) {
        if(request <= 15){
            System.out.println("院长审批通过");
        }else{
            if(next != null){
                next.handlerRequest(request);
            }else{
                System.out.println("无法审批");
            }
        }
    }
}
