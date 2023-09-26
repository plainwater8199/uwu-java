package com.uwu.study.dp.structure.composite;

import java.util.List;

public abstract class AbstractFile {
    protected String name;

    public void printName(){
        System.out.println(name);
    }

    public abstract boolean add(AbstractFile file);
    public abstract boolean remove(AbstractFile file);
    public abstract List<AbstractFile> getChildren();
}
