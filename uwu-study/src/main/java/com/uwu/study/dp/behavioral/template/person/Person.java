package com.uwu.study.dp.behavioral.template.person;

public abstract class Person {
    public void templateMethod(){
        System.out.println("上课 去教室");
        primitiveOperation1();
        System.out.println("下课 离开教室");
        primitiveOperation2();
    }

    public abstract void primitiveOperation1();
    public abstract void primitiveOperation2();


}
