package com.uwu.study.dp.behavioral.memento;


/**
 * 在不破坏封闭的前提下，捕获一个对象的内部状态，并在该对象之外保存这个状态。这样以后就可将该对象恢复到原先保存的状态。
 *
 *
 * 1.Originator(发起人):负责创建一个备忘录Memento，用以记录当前时刻自身的内部状态，并可使用备忘录恢复内部状态。
 * Originator可以根据需要决定Memento存储自己的哪些内部状态。
 *
 * 2.Memento(备忘录):负责存储Originator对象的内部状态，并可以防止Originator以外的其他对象访问备忘录。
 * 备忘录有两个接口:Caretaker只能看到备忘录的窄接口，他只能将备忘录传递给其他对象。
 * Originator却可看到备忘录的宽接口，允许它访问返回到先前状态所需要的所有数据。
 *
 * 3.Caretaker(管理者):负责备忘录Memento，不能对Memento的内容进行访问或者操作。
 */
public class MementoPatternTest {
    public static void main(String[] args) {
        Caretaker caretaker = new Caretaker();
        Originator originator = new Originator();


        originator.setState("1024");
        Memento backup1 = originator.createMemento();
        caretaker.addMemento(backup1);


        originator.setState("2048");
        Memento backup2 = originator.createMemento();
        caretaker.addMemento(backup2);


        originator.setState("4096");
        Memento backup3 = originator.createMemento();
        caretaker.addMemento(backup3);

        System.out.println(originator.getState());

        caretaker.showMemento();

        Memento memento1 = caretaker.getMemento(2);
        originator.setMemento(memento1);

        System.out.println("根据第二次备份还原之后的状态为："+originator.getState());


        caretaker.showMemento();
    }
}
