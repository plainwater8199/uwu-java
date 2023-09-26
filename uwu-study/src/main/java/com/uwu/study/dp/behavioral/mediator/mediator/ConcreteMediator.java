package com.uwu.study.dp.behavioral.mediator.mediator;


import com.uwu.study.dp.behavioral.mediator.colleague.Colleague;
import com.uwu.study.dp.behavioral.mediator.colleague.Colleague1;
import com.uwu.study.dp.behavioral.mediator.colleague.Colleague2;

public class ConcreteMediator extends Mediator{
    private Colleague1 colleague1;
    private Colleague2 colleague2;

    public void setColleague1(Colleague1 colleague1) {
        this.colleague1 = colleague1;
    }

    public void setColleague2(Colleague2 colleague2) {
        this.colleague2 = colleague2;
    }

    @Override
    public void sendMessage(String message, Colleague colleague) {
        if(colleague == colleague1){
            colleague2.notify(message);
        }else{
            colleague1.notify(message);
        }
    }
}
