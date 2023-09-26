package com.uwu.study.dp.behavioral.memento;

import java.util.ArrayList;
import java.util.List;

public class Caretaker {
    private List<Memento> mementoList = new ArrayList<>();

    public void addMemento(Memento memento){
        mementoList.add(memento);
    }

    public Memento getMemento(int index){
        if(index >= 1 && index <= mementoList.size()){
            return  mementoList.get(index-1);
        }
        return null;
    }

    public void showMemento(){
        int cnt = 1;
        for(Memento memento : mementoList){
            System.out.println("第"+cnt+"次备份，状态为："+memento.getState());
            cnt++;
        }
    }
}
