package com.uwu.study.dp.structure.composite;

import java.util.ArrayList;
import java.util.List;

public class Folder extends AbstractFile{
    private List<AbstractFile> childrenList = new ArrayList<>();

    public Folder(String name){
        this.name = name;
    }
    @Override
    public boolean add(AbstractFile file) {
        return childrenList.add(file);
    }

    @Override
    public boolean remove(AbstractFile file) {
        return childrenList.remove(file);
    }

    @Override
    public List<AbstractFile> getChildren() {
        return childrenList;
    }
}
