package com.uwu.study.dp.behavioral.chainofresponsibility.handler;

public class XiaoZhang extends Handler{
    @Override
    public void handlerRequest(int request) {
        if(request <= 30){
            System.out.println("校长审批通过");
        }else{
            if(next != null){
                next.handlerRequest(request);
            }else{
                System.out.println("无法审批");
            }
        }
    }
}
