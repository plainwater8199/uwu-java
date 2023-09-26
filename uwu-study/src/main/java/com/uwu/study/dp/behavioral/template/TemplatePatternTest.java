package com.uwu.study.dp.behavioral.template;


import com.uwu.study.dp.behavioral.template.person.Person;
import com.uwu.study.dp.behavioral.template.person.Student;
import com.uwu.study.dp.behavioral.template.person.Teacher;

/**
 * 在模板模式（Template Pattern）中，一个抽象类公开定义了执行它的方法的方式/模板。它的子类可以按需要重写方法实现，但调用将以抽象类中定义的方式进行。这种类型的设计模式属于行为型模式。
 *
 * 模板模式的特点
 * 1、子类按需要重写父类中的方法。
 * 2、以抽象父类中的定义进行调用。
 */
public class TemplatePatternTest {
    public static void main(String[] args) {
        Person student = new Student();
        Person teacher = new Teacher();

        student.templateMethod();


        System.out.println("-------------------");

        teacher.templateMethod();
    }
}
