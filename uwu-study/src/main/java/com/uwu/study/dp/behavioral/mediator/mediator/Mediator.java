package com.uwu.study.dp.behavioral.mediator.mediator;


import com.uwu.study.dp.behavioral.mediator.colleague.Colleague;

public abstract class Mediator {
    public abstract void sendMessage(String message, Colleague colleague);
}
