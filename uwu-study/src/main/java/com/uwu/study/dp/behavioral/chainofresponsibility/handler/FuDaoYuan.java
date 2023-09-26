package com.uwu.study.dp.behavioral.chainofresponsibility.handler;

public class FuDaoYuan extends Handler{
    @Override
    public void handlerRequest(int request) {
        if(request <= 7){
            System.out.println("辅导员审批通过");
        }else{
            if(next != null){
                next.handlerRequest(request);
            }else{
                System.out.println("无法审批");
            }
        }
    }
}
